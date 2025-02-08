package data.repository

import data.network.rest.ApiService
import data.network.rest.model.mappers.toDomain
import data.network.rest.model.requests.LoginRequest
import domain.common.Result
import domain.model.User
import domain.repository.AuthRepository
import java.security.MessageDigest

class AuthRepositoryImpl(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(username: String, password: String): Result<User> = try {
        val hashedPassword = hashPassword(password)
        val request = LoginRequest(username, hashedPassword)
        val response = apiService.login(request)
        Result.Success(response.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        val bytes = md.digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}