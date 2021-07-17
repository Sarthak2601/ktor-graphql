package com.sarthak.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoClient
import com.sarthak.models.User
import com.sarthak.models.UserInput
import com.sarthak.models.UserResponse
import com.sarthak.repository.UserRepository
import io.ktor.application.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class AuthService: KoinComponent {
    private val client: MongoClient by inject()
    private val repository = UserRepository(client)
    private val secret: String = "Secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun signIn(userInput: UserInput): UserResponse?{
        val user = repository.getUserByEmail(userInput.email) ?: error("No such user exists")
        // hash the incoming password and compare it to the saved password
        if (BCrypt.verifyer().verify(
                userInput.password.toByteArray(Charsets.UTF_8),
                user.hashPass
            ).verified){
            val token = signAccessToken(user.id)
            return UserResponse(token, user)
        }
        error("Password is incorrect")
    }

    fun signUp(userInput: UserInput): UserResponse?{
        val hashedPassword = BCrypt.withDefaults().hash(10, userInput.password.toByteArray(Charsets.UTF_8))
        val emailUser = repository.getUserByEmail(userInput.email)
        if (emailUser != null){
            error("User already exists!")
        }
        val id = UUID.randomUUID().toString()
        val newUser = repository.add(User(id, userInput.email, hashedPassword))
        val token = signAccessToken(newUser.id)
        return UserResponse(token, newUser)
    }

    fun verifyToken(call: ApplicationCall): User?{
        return try {
            val authHeader = call.request.headers["Authorization"]?: ""
            val token = authHeader.split("Bearer ").last()
            val accessToken = verifier.verify(JWT.decode(token))
            val userId = accessToken.getClaim("userId").asString()
            User(id = userId, email = "", hashPass = ByteArray(0))
        }catch (e: Exception){
            null
        }
    }

    private fun signAccessToken(id: String): String {
        return JWT.create()
            .withIssuer("example")
            .withClaim("userId", id)
            .sign(algorithm)
    }
}