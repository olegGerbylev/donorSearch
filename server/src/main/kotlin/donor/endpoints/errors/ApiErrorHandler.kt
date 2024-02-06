package donor.endpoints.errors

import com.fasterxml.jackson.databind.node.ObjectNode
import donor.utils.JSON
import org.apache.commons.lang3.text.StrSubstitutor
import org.postgresql.util.PSQLException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@ControllerAdvice
class ApiErrorHandler : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger("ErrorHandler")

    @ExceptionHandler(WebException::class)
    fun handleWebException(ex: WebException): ResponseEntity<String> {
        return makeResponse(ex.errors, ex)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<String> {
        return makeResponse(listOf(ApiError(ApiErrorCode.INTERNAL_SERVER_ERROR, ex)), ex)
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException::class)
    fun handleEntityNotFound(ex: JpaObjectRetrievalFailureException, request: HttpServletRequest): ResponseEntity<String> {
        val enf = ex.cause as EntityNotFoundException
        val params = Regex("Unable to find eventos\\.dao\\.([\\w.]+) with id (.+)")
                .matchEntire(enf.message!!)?.groupValues ?: listOf("", "", "")
        val entity = params[1]
        val id = params[2]
        val uri = request.requestURI

        return makeResponse(listOf(
            ApiError(
                ApiErrorCode.RESOURCE_NOT_FOUND,
                "id" to id, "entity" to entity, "uri" to uri
                )
        ), ex)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(ex: DataIntegrityViolationException): ResponseEntity<String> {
        val pe = getPSQLException(ex)
        return if (pe == null) {
            makeResponse(listOf(ApiError(ApiErrorCode.DATA_INTEGRITY_VIOLATION)), ex)
        } else {
            val key = extractKey(pe)
            val code = ApiErrorCode.values().find { key.first == it.dbErrorKey } ?: run {
                ApiErrorCode.DATA_INTEGRITY_VIOLATION
            }
            makeResponse(listOf(
                ApiError(code,
                "key" to key.first,
                "keys" to key.second.joinToString())
            ), ex)
        }
    }

    private fun extractKey(pe: PSQLException): Pair<String, List<String>> {
        val parts = pe.message!!.split("\n")

        val params = Regex(""".*"(.*)".*""")
                .matchEntire(parts[0])?.groupValues ?: emptyList<String>()
        val key = params.getOrElse(1) {""}

        val keys:List<String> = parts.subList(1, parts.size).mapNotNull {
            Regex(""".*"(.*)".*""")
                    .matchEntire(it)?.groupValues?.getOrNull(1)
        }
        return Pair(key, keys)
    }

    private fun getPSQLException(ex: Throwable, level: Int = 0): PSQLException? {
        return if (ex is PSQLException) {
            ex
        } else {
            if (ex.cause != null && level < 10) {
                getPSQLException(ex.cause!!, level + 1)
            } else {
                null
            }
        }
    }


     fun handleExceptionInternal(ex: Exception, body: Any?, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        when (ex) {
            is NoHandlerFoundException -> {
                @Suppress("UNCHECKED_CAST")
                return handleNotFound(ex) as ResponseEntity<Any>
            }
            is HttpMediaTypeNotSupportedException -> {
                @Suppress("UNCHECKED_CAST")
                return handleMediaTypeNotSupported(ex) as ResponseEntity<Any>
            }
            else -> {

                val json = JSON.objectNode().put("error", status.name)

                val responseHeaders = HttpHeaders()
                headers.contentType = MediaType.APPLICATION_JSON_UTF8

                @Suppress("UNCHECKED_CAST")
                return ResponseEntity(json.toString(), responseHeaders, status) as ResponseEntity<Any>
            }
        }
    }

    fun handleNotFound(ex: NoHandlerFoundException): ResponseEntity<String> {
        return makeResponse(listOf(
            ApiError(
                ApiErrorCode.HANDLER_NOT_FOUND,
                "method" to ex.httpMethod, "uri" to ex.requestURL)
        ), ex)
    }

    fun handleMediaTypeNotSupported(ex: HttpMediaTypeNotSupportedException): ResponseEntity<String> {
        val supported = ex.supportedMediaTypes.joinToString { it.toString() }
        return makeResponse(listOf(
            ApiError(
                ApiErrorCode.UNSUPPORTED_MEDIA_TYPE,
                "type" to (ex.contentType?.toString() ?: ""), "supported" to supported)
        ), ex)
    }

//    private fun handleSpringException(ex: Exception, status: HttpStatus): ResponseEntity<Any> {
//        val exception = buildException(ex, status)
//        return ResponseEntity
//                .status(exception.status)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .body(JSON.stringify(exception.body))
//    }

    private fun makeResponse(errors: List<ApiError>, ex: Exception): ResponseEntity<String> {
        val (json, status) = renderErrors(errors)

        logApiError(json, status, ex)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        return ResponseEntity(json.toString(), headers, status)
    }

    fun writeResponse(error: ApiError, response: HttpServletResponse) {
        val (json, status) = renderErrors(listOf(error))

        logApiError(json, status)

        response.contentType = "application/json;charset=utf-8"
        response.writer.println(json.toString())
        response.status = status.value()
    }


    private fun logApiError(json: ObjectNode, status: HttpStatus, ex: Exception? = null) {
        val stacktrace = StringWriter().also { ex?.printStackTrace(PrintWriter(it)) }.toString().trim()
        val logString = if (stacktrace.isEmpty()) {
            "Api error: $status, body: $json"
        } else {
            "Api error: $status, body: $json, exception: $stacktrace"
        }
        if (status.value() >= 400 && ex !is WebException && ex !is HttpClientErrorException.UnsupportedMediaType
            || ex is WebException && status.is5xxServerError) {
            log.error(logString)
        } else {
            log.debug(logString)
        }
    }

    private fun renderErrors(errors: List<ApiError>): Pair<ObjectNode, HttpStatus> {
        val mainError = selectMainError(errors)
        val json = buildJsonError(mainError)
        if (errors.size > 1) {
            val errs = JSON.mapper.createArrayNode().addAll(
                    errors.map { buildJsonError(it) }
            )
            json.replace("errors", errs)
        }
        if (mainError.error.status.value() >= 500) {
            json.put("uuid", UUID.randomUUID().toString())
        }
        return Pair(json, mainError.error.status)
    }

    companion object {
        fun buildJsonError(error: ApiError): ObjectNode {
            val message =
                    if (error.args.isEmpty()) {
                        error.error.message
                    } else {
                        StrSubstitutor.replace(error.error.message, error.args)
                    }

            val json = JSON.objectNode()
                    .put("error", error.error.name)
                    .put("message", message)
            if (error.args.isNotEmpty()) {
                json.replace("args", JSON.toNode(error.args))
            }
            return json
        }
    }

    private fun selectMainError(errors: List<ApiError>): ApiError {
        if (errors.isEmpty()) {
            return ApiError(ApiErrorCode.INTERNAL_SERVER_ERROR)
        }
        return errors.maxByOrNull { e -> e.error.status.value() }!!
    }

}
