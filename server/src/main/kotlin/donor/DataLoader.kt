package donor

import donor.dao.Account
import donor.dao.AccountRepository
import donor.services.AccountService
import donor.utils.ClockHolder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val accounts: AccountRepository,
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
            permission = "superAdmin",
            lastActive = ClockHolder.instantNow(),
        )
        accounts.save(account)
    }
}