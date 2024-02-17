package donor.dao

import donor.api.model.SessionInfoData
import donor.utils.ClockHolder
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

@Entity(name ="sessions")
data class Session (
    @Id
    val sessionId: String,
    val accountId: Long,
    val superAdmin: Boolean = false,
    val deletedTs: Instant,
    val creationDate: Instant = ClockHolder.instantNow()
    )

interface SessionRepository: JpaRepository<Session,String>{

    @Query("SELECT * FROM sessions WHERE deleted_ts <= :currentTime LIMIT :limit", nativeQuery = true)
    fun findExpired(currentTime: Instant, limit: Int): List<Session>

}

fun Session.toApi(account: Account): SessionInfoData{
    return SessionInfoData(
        sessionId = this.sessionId,
        accountId = this.accountId,
        role = account.role,
        displayName = account.displayName,
        login = account.email
    )
}