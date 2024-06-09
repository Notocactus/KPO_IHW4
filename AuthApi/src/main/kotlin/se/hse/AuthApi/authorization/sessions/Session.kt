package se.hse.AuthApi.authorization.sessions

import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.sql.Timestamp

@Entity
@Table(name = "sessions")
@NoArgsConstructor
data class Session(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var userID: Int,

    val token: String,

    val expires: Timestamp,
)