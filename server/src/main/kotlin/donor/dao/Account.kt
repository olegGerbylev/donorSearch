package donor.dao

import donor.api.model.AccountData
import donor.utils.ClockHolder
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

    val email: String,
    val password: String,
    val role: String,

    val active: Boolean,
    val confirmed: Boolean,

    val displayName: String,
    val photo: String?,
    val phone: String?,
    val city: String?,
    val bio: String?,

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
        role = this.role,
        phone = this.phone,
        city = this.city,
        bio = this.bio,
        photo = this.photo,
    )
}

fun AccountData.toDBData(email: String, password: String, photo: String?): Account {
    return Account(
        id = this.id!!,

        displayName = this.displayName!!,
        email = email,
        password = password,
        role = this.role!!,
        phone = this.phone,
        city = this.city,
        bio = this.bio,
        photo = photo,
        active = this.active!!,
        confirmed = this.confirmed!!,
    )
}
