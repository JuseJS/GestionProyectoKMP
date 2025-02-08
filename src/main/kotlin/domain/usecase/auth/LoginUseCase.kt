package domain.usecase.auth

import domain.common.Result
import domain.common.UseCase
import domain.model.User
import domain.repository.AuthRepository

data class LoginParams(
    val username: String,
    val password: String
)

class LoginUseCase(
    private val repository: AuthRepository
) : UseCase<LoginParams, User>() {
    override suspend operator fun invoke(parameters: LoginParams): Result<User> =
        repository.login(parameters.username, parameters.password)
}