package donor.endpoints.errors


import jakarta.servlet.ServletException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class AuthenticationErrorHandler : AuthenticationEntryPoint {
    private val log = LoggerFactory.getLogger("ErrorHandler")

    @Autowired
    private lateinit var errorHandler: ApiErrorHandler

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authenticationException: AuthenticationException) {
        if (authenticationException is InsufficientAuthenticationException) {
            errorHandler.writeResponse(ApiError(ApiErrorCode.AUTHENTICATION_REQUIRED), response)
        } else {
            log.error("AuthenticationErrorHandler", authenticationException)
            errorHandler.writeResponse(ApiError(ApiErrorCode.AUTHENTICATION_REQUIRED, authenticationException), response)
        }
    }
}
