package se.hse.AuthApi.authorization.sessions

import org.springframework.data.jpa.repository.JpaRepository
import se.hse.AuthApi.authorization.sessions.Session
import java.sql.Timestamp

interface SessionRepository: JpaRepository<Session, Long> {

    fun findByUserID(userID: Int): Session?
    fun findByToken(token: String): Session?

    fun findByExpiresLessThan(timestamp: Timestamp): List<Session>

    fun deleteById(id: Int?): Long
}