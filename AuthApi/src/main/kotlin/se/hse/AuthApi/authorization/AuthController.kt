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

@RestController
@RequestMapping("/user")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDto): ResponseEntity<String>{
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