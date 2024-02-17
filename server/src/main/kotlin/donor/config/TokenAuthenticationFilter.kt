package donor.config

import donor.dao.Account
import donor.dao.AccountRepository
import donor.dao.Session
import donor.dao.SessionRepository
import donor.endpoints.errors.ApiError
import donor.endpoints.errors.ApiErrorCode
import donor.endpoints.errors.WebException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenAuthenticationFilter(
    private val tokens: SessionRepository,
    private val account: AccountRepository
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader.doesNotContainBearerToken()) {
            filterChain.doFilter(request, response)
            return
        }

        val authToken: Session? = tokens.findById(authHeader!!.extractTokenValue()).orElse(null)

        if (authToken != null && SecurityContextHolder.getContext().authentication == null){
            val foundUser = account.findById(authToken.accountId).orElse(null) ?: return throwError(request, filterChain, response, ApiErrorCode.INVALID_ACCESS_TOKEN_FOR_ACCOUNT)
            updateContext(foundUser, request, authToken)
        }
        filterChain.doFilter(request, response)
    }

    private fun throwError(request: HttpServletRequest, filterChain: FilterChain, response: HttpServletResponse, error: ApiErrorCode) {
        request.setAttribute("AEF_ERROR", ApiError(error))
        filterChain.doFilter(request, response)
    }

    private fun updateContext(foundUser: Account, request: HttpServletRequest, token: Session) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, token)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun String?.doesNotContainBearerToken(): Boolean =
        this == null || !this.startsWith("Bearer ")

    private fun String.extractTokenValue(): String =
        this.substringAfter("Bearer ")
}