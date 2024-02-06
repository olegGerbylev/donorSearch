package donor.dao

import donor.api.model.AccountData
import donor.utils.ClockHolder
import donor.utils.DateUtils
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant


@Entity(name = "accounts")
data class Account(
    @Id
    val id: Long,

    val displayName: String,
    val email: String,
    val password: String,

    val active: Boolean,
    val confirmed: Boolean,

    val permission: String,

    val lastActive: Instant,
    @Column(updatable = false)
    val creationDate: Instant = ClockHolder.instantNow(),
)

interface AccountRepository: JpaRepository<Account, Long>{
    @Query("SELECT nextval('account_id_seq')", nativeQuery = true)
    fun nextId(): Long

    @Query("SELECT * FROM accounts WHERE email = :email AND active IS TRUE", nativeQuery = true)
    fun findByLogin(email: String): Account?

    @Query("SELECT * FROM accounts WHERE id = :id AND active IS TRUE", nativeQuery = true)
    fun findActiveAccountById(id: Long): Account?

    companion object {
        const val SUPER_ADMIN_ID = -1L
    }
}

fun Account.toApiData(): AccountData{
    return AccountData(
        id = this.id,
        displayName = this.displayName,
        active = this.active,
        confirmed = this.confirmed,
        permission = this.permission,
        superAdmin = this.id == -1L,
    )
}

fun AccountData.toDBData(email: String, password: String): Account {
    return Account(
        id = this.id!!,

        displayName = this.displayName!!,
        email = email,
        password = password,

        active = this.active!!,
        permission = this.permission!!,
        confirmed = this.confirmed!!,
        lastActive = ClockHolder.instantNow(),
    )
}
