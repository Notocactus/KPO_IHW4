package se.hse.AuthApi.authorization.users

data class UserDto(
    val username: String, val email: String, val password: String
)
