package se.hse.AuthApi.authorization.users

import org.springframework.data.jpa.repository.JpaRepository
import se.hse.AuthApi.authorization.users.User

interface UserRepository: JpaRepository<User, Long> {

    fun findById(id: Int): User?
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
    fun findByPassword(password: Int): User?
    fun findByEmailAndPassword(email: String, password: Int): User?
}