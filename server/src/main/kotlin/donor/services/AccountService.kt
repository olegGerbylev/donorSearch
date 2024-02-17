package donor.services

import donor.api.model.AccountData
import donor.api.model.AccountRegisterData
import donor.dao.*
import donor.endpoints.errors.ApiErrorCode
import donor.endpoints.errors.WebException
import donor.utils.ClockHolder
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.time.Duration



@Service
class AccountService(
    private val accounts: AccountRepository,
    private val confirmations: TokenConfirmationRepository,
    private val mailService: EmailService,
    @Value("\${server.externalUrl}") private val externalUrl: String,
//    @Value("\${mail.contactMail}") private val contactMail: String,
//    @Value("\${server.test}") private val testMode: Boolean,
) {

    fun authenticate(login: String, password: String): Account? {
        val user = accounts.findByLogin(login) ?: return null
        if (!BCrypt.checkpw(password, user.password)) {
            return null
        }
        return user
    }

    fun createAccount(accountRegisterData: AccountRegisterData, confirmed: Boolean = false) : Account{
        val accountId = accounts.nextId()
        val accountData = AccountData(
            id = accountId,
            displayName = accountRegisterData.displayName,
            active = true,
            confirmed = confirmed,
            role = "admin",
        )
        val acc = accountData.toDBData(email = accountRegisterData.login!!, password = encryptPassword(accountRegisterData.password!!), photo = null)
        accounts.save(acc)
        return acc
    }

    fun sendRegistrationConfirm(account: Account){
        val previousConfirmation =confirmations.findById(account.id).orElse(null)
        if (previousConfirmation != null){
            confirmations.delete(previousConfirmation)
        }
        val token = RandomStringUtils.randomAlphanumeric(40)
        val expireTime =
            ClockHolder.instantNow().plus(Duration.ofDays(1))
        val confirmation =
            TokenConfirmation(token = token, id = account.id, deletedTs = expireTime)
        confirmations.save(confirmation)
        try {
            val emailTo = account.email

            val title: String = "Подтверждение почтового адреса"
            val body: String = "$externalUrl/registration-completed/$token"
            mailService.sendSimpleMessage(emailTo, title, body)

        } catch (e: Exception) {
        }
    }

    fun confirmAccount(token: String){
        val tokenConfirmation = confirmations.findByToken(token) ?: throw WebException(ApiErrorCode.ROTTEN_TOKEN)
        if (tokenConfirmation.deletedTs < ClockHolder.instantNow()){
            confirmations.delete(tokenConfirmation)
            throw WebException(ApiErrorCode.ROTTEN_TOKEN)
        }
        accounts.findActiveAccountById(tokenConfirmation.id)?.let {
            val updateAccount = it.copy(confirmed = true)
            accounts.save(updateAccount)
        }
        confirmations.delete(tokenConfirmation)
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun jobCleanConfirmationsExpired(){
        val ucsExpired = confirmations.findExpired(ClockHolder.instantNow(), 100)
        if (ucsExpired.isEmpty()) return
        accounts.deleteAllById(ucsExpired.map { it.id })
        confirmations.deleteAll(ucsExpired)
    }


    companion object {
        fun encryptPassword(password: String): String {
            val salt = BCrypt.gensalt()
            return BCrypt.hashpw(password, salt)
        }
    }
}