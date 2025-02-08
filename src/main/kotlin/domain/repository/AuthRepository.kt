package domain.repository

import domain.common.Result
import domain.model.User

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
}