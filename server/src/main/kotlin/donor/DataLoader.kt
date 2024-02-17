package donor

import donor.dao.Account
import donor.dao.AccountRepository
import donor.dao.TokenConfirmationRepository
import donor.services.AccountService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val accounts: AccountRepository,
    private val confirmations: TokenConfirmationRepository,
    @Value("\${defaults.firstUser.username}") private val login: String,
    @Value("\${defaults.firstUser.password}") private val password: String,
):CommandLineRunner {
    override fun run(vararg args: String?) {
        val account = Account(
            id = -1L,
            displayName = login,
            email = login,
            password = AccountService.encryptPassword(password),
            active = true,
            confirmed = true,
            role = "superAdmin",
            bio = "superAdmin",
            city = "Cloud",
            phone = "+793124534345",
            photo = null,
        )
        accounts.save(account)
    }
}