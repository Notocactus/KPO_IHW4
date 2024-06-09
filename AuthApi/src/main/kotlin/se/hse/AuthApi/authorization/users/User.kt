package se.hse.AuthApi.authorization.users

import jakarta.persistence.*
import lombok.NoArgsConstructor

@Entity
@Table(name = "users")
@NoArgsConstructor
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    val username: String,
    val email: String,
    var password: Int,
)