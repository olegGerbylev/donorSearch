package donor.endpoints

import donor.api.AuthApiService
import donor.api.model.AccountRegisterData
import donor.api.model.LoginRequestData
import donor.api.model.SessionInfoData
import donor.dao.AccountRepository
import donor.dao.toApi
import donor.endpoints.errors.ApiErrorCode
import donor.endpoints.errors.WebException
import donor.endpoints.validation.AccountApiValidator
import donor.endpoints.validation.Validator
import donor.services.AccountService
import donor.services.EmailService
import donor.services.SessionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service
import java.net.URI

@Service
class Auth(
    private val validator: AccountApiValidator,
    private val accounts: AccountService,
    private val sessions: SessionService,
    @Value("\${server.externalUrl}") val externalUrl: String,
): AuthApiService {
    override fun login(loginRequestData: LoginRequestData): SessionInfoData {
        TODO("Not yet implemented")
        Validator(loginRequestData) {
            isNotNullOrEmpty("login")
            isNotNullOrEmpty("password")
        }
        val acc = accounts.authenticate(loginRequestData.login!!, loginRequestData.password!!) ?: throw WebException(ApiErrorCode.AUTH_FAILED)
        return sessions.createSession(acc)
    }

    override fun checkSession(): SessionInfoData {
        TODO("Not yet implemented")
        return sessions.checkSession()
    }

    override fun logout() {
        TODO("Not yet implemented")
        val sessionInfoData = sessions.getCurrentSessionReq()
        sessions.deletedSession(sessionInfoData)
    }

    override fun registerAccount(accountRegisterData: AccountRegisterData) {
        TODO("Not yet implemented")
        validator.validateRegisterAccount(accountRegisterData)
        val acc = accounts.createAccount(accountRegisterData)
        accounts.sendRegistrationConfirm(acc)
    }

    override fun confirmAccount(confirmToken: String) {
        TODO("Not yet implemented")
        accounts.confirmAccount(confirmToken)
    }
}
