package se.hse.AuthApi.authorization.users

data class LoginUserDto(
    val email: String, val password: String
)