package donor.endpoints.errors

open class WebException(
        var errors: List<ApiError>
) : RuntimeException() {

    constructor(error: ApiErrorCode, args: Map<String, String> = emptyMap()) : this(listOf(ApiError(error, args)))

}

class WebExceptionNotFound(errors: List<ApiError>) : WebException(errors) {

    constructor(entity: String, id: Any) : this(listOf(
        ApiError(
            ApiErrorCode.RESOURCE_NOT_FOUND,
            "id" to id.toString(), "entity" to entity)
    ))

}

class WebExceptionInternal(errors: List<ApiError>) : WebException(errors) {

    constructor(message: String) : this(listOf(
        ApiError(
            ApiErrorCode.INTERNAL_SERVER_ERROR,
            "message" to message)
    ))
}