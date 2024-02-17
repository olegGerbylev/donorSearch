package donor.endpoints.validation

import donor.api.model.AccountRegisterData
import donor.api.model.LoginRequestData
import donor.dao.AccountRepository
import donor.endpoints.errors.ApiErrorCode
import org.springframework.stereotype.Service

@Service
class AccountApiValidator(
    val accounts: AccountRepository,
//                          val sessions: SessionService,
) {

    fun validateRegisterAccount(data: AccountRegisterData) {
        Validator(data) {
            length("login", min = 4, max = 64)
            length("password", min = 6, max = 64)
            length("displayName", min = 3, max = 128)
            email("login")
        }
        Validator(data) {
            if (accounts.findByLogin(data.login!!) != null) {
                error("ownerEmail", ApiErrorCode.EMAIL_ALREADY_EXIST)
            }
            if (data.login.contains(Regex("\\+.*gmail.com", RegexOption.IGNORE_CASE))) {
                error("ownerEmail", ApiErrorCode.EMAIL_ALIASED)
            }
        }
    }

    fun validateLoginAccount(data: LoginRequestData){
        Validator(data){
            isNotNullOrEmpty("login")
            isNotNullOrEmpty("password")
        }
    }
}
