package donor.dao

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

@Entity
data class TokenConfirmation (
    @Id
    val id: Long,
    val token: String,
    val expireTime: Instant
    )

interface TokenConfirmationRepository: JpaRepository<TokenConfirmation, Long>{
    @Query("SELECT * FROM token_confirmation WHERE token = :token", nativeQuery = true)
    fun findByToken(token: String): TokenConfirmation?
}