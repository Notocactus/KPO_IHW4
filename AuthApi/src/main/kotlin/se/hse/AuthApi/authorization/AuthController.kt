package se.hse.AuthApi.authorization

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import se.hse.AuthApi.authorization.users.LoginUserDto
import se.hse.AuthApi.authorization.sessions.SessionDto
import se.hse.AuthApi.authorization.users.UserDto
import java.util.regex.Pattern

@RestController
@RequestMapping("/user")
class AuthController(val authService: AuthService) {

    val pattern: Pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

    fun validateEmail(email: String): Boolean {
        return pattern.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8 &&
                password.contains(Regex("[A-Z]")) && password.contains(Regex("[a-z]")) &&
                password.contains(Regex("[0-9]")) && password.contains(Regex("[!@#$%^&*+.,-]"))
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDto): ResponseEntity<String>{
        if (!validateEmail(user.email)){
            ResponseEntity<String>("Incorrect Email", HttpStatus.BAD_REQUEST)
        }
        if (!validatePassword(user.password) ){
            ResponseEntity<String>("Incorrect password.\n The password must consist of at least eight characters, " +
                    "including letters of both cases, digits and special characters (!@#$%^&*+.,-)", HttpStatus.BAD_REQUEST)
        }
        return try {
            val resp = authService.registerUser(user)
            ResponseEntity.ok(resp)
        } catch (e:Exception){
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginUserDto: LoginUserDto): ResponseEntity<String>{
        return try {
            if (!validateEmail(loginUserDto.email)){
                ResponseEntity<String>("Incorrect Email", HttpStatus.BAD_REQUEST)
            }
            if (!validatePassword(loginUserDto.password) ){
                ResponseEntity<String>("Incorrect password.\n The password must consist of at least eight characters, " +
                        "including letters of both cases, digits and special characters (!@#$%^&*+.,-)", HttpStatus.BAD_REQUEST)
            }
            val resp = authService.login(loginUserDto)
            ResponseEntity.ok(resp)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }

    }

    @DeleteMapping("/logout")
    fun logout(@RequestBody sessionDto: SessionDto): ResponseEntity<String>{
        return try {
            val resp = authService.logout(sessionDto)
            ResponseEntity.ok(resp)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/userinfo")
    fun getUserInfo(@RequestBody sessionDto: SessionDto): ResponseEntity<String>{
        return try {
            val resp = authService.getUserInfo(sessionDto)
            ResponseEntity.ok(resp)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        }
    }
}