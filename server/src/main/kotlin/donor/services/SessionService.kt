package donor.services

import donor.api.model.SessionInfoData
import donor.dao.*
import donor.endpoints.errors.ApiErrorCode
import donor.endpoints.errors.WebException
import donor.utils.ClockHolder
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class SessionService (
    private val sessions: SessionRepository,
    private val accountRepository: AccountRepository,
){
    fun createSession(account: Account): SessionInfoData {
        val id = RandomStringUtils.randomAlphanumeric(40)
        val time:Instant
        if (account.id == AccountRepository.SUPER_ADMIN_ID){
            time = ClockHolder.instantNow().plus(Duration.ofMinutes(15L))
        }else{
            time = ClockHolder.instantNow().plus(Duration.ofDays(365))
        }
        val session = Session(sessionId = id,
            accountId = account.id ,
            superAdmin = account.id == AccountRepository.SUPER_ADMIN_ID,
            deletedTs = time,
            )
        sessions.save(session)
        return session.toApi(account)
    }

    fun checkSession(): SessionInfoData {
        val currentSession = getCurrentSessionReq()
        val acc = accountRepository.findById(currentSession.accountId).get()
        return currentSession.toApi(acc)
    }

    fun deletedSession(session: Session){
        sessions.delete(session)
    }

    fun isSuperAdmin():Boolean{
        val session = getCurrentSessionReq()
        if (session.accountId == AccountRepository.SUPER_ADMIN_ID) return true
        throw WebException(ApiErrorCode.NO_PERMISSION, mapOf("permission" to "supperAdmin"))
    }

    fun getCurrentSessionReq(): Session {
        return getCurrentSession() ?: throw WebException(ApiErrorCode.INVALID_ACCESS_TOKEN)
    }
    fun getCurrentSession(): Session? {
        val securityContext = SecurityContextHolder.getContext()
        if (securityContext?.authentication == null) {
            return null
        }
        if (securityContext.authentication is AnonymousAuthenticationToken) {
            return null
        }
        val session = securityContext.authentication.credentials as Session? ?: return null
        if (session.deletedTs < ClockHolder.instantNow()){
            return null
        }
        return session
    }


    @Scheduled(cron = "0 0 0 * * *")
    fun jobCleanConfirmationsExpired(){
        val ucsExpired = sessions.findExpired(ClockHolder.instantNow(), 100)
        if (ucsExpired.isEmpty()) return
        sessions.deleteAll(ucsExpired)
    }
}