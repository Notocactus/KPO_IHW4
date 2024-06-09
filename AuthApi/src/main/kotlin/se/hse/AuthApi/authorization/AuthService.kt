package se.hse.AuthApi.authorization

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import se.hse.AuthApi.authorization.sessions.Session
import se.hse.AuthApi.authorization.users.LoginUserDto
import se.hse.AuthApi.authorization.sessions.SessionDto
import se.hse.AuthApi.authorization.sessions.SessionRepository
import se.hse.AuthApi.authorization.users.UserDto
import se.hse.AuthApi.authorization.users.User
import se.hse.AuthApi.authorization.users.UserRepository
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.regex.Pattern

@Service
class AuthService(private val userRepository: UserRepository, private  val sessionRepository: SessionRepository) {



    @Transactional
    fun registerUser(user: UserDto): String{
        if (userRepository.findByEmail(user.email) != null){
            throw IllegalAccessException("The user is already registered with this email")
        }
        else if (userRepository.findByUsername(user.username) != null){
            throw IllegalAccessException("The user is already registered with this nickname")
        }
        userRepository.saveAndFlush(User(username = user.username, email = user.email, password = user.password.hashCode() ))
        return "The user is registered"
    }

    @Transactional
    fun login(user: LoginUserDto): String {
        removeExpiredTokens()
        userRepository.findByEmail(user.email)?.id
            ?: throw IllegalAccessException("The user doesn't exist with this email")

        val userId = userRepository.findByEmailAndPassword(user.email, user.password.hashCode())?.id
            ?: throw IllegalAccessException("Wrong password")
        val checkSession: Session? = sessionRepository.findByUserID(userId)
        println(userId)
            if (checkSession != null) {
                throw IllegalAccessException("The user is already logged in")
            }
        var userToken: String
        do {
            userToken = getToken()
        } while (sessionRepository.findByToken(userToken) != null)
        val date: LocalDateTime = LocalDateTime.now().plusDays(30)
        sessionRepository.save(Session(token = userToken, expires = Timestamp.valueOf(date), userID = userId))
        return "{\"token\": $userToken}"
    }

    @Transactional
    fun logout(sessionDto: SessionDto): String{
        removeExpiredTokens()
        val session = sessionRepository.findByToken(sessionDto.token) ?: throw IllegalAccessException("User isn't logged in")
        sessionRepository.deleteById(session.id)
        return "User is logged out"
    }

    fun getUserInfo(sessionDto: SessionDto): String{
        removeExpiredTokens()
        val session = sessionRepository.findByToken(sessionDto.token) ?: throw IllegalAccessException("User isn't logged in")
        val userObj = userRepository.findById(session.userID)
        return "{\"id\" : ${userObj!!.id}, \"username\" : ${userObj!!.username}, \"email\" : ${userObj!!.email}}"
    }

    fun removeExpiredTokens(){
        val date: LocalDateTime = LocalDateTime.now().plusDays(30)
        val zoneId = ZoneId.systemDefault()
        val sessions = sessionRepository.findByExpiresLessThan(Timestamp(date.atZone(zoneId).toEpochSecond()))
        for (session in sessions){
            sessionRepository.deleteById(session.id)
        }
        return
    }

    fun getToken(): String{
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val token: String = List(20) { alphabet.random() }.joinToString("")
        return token
    }
}