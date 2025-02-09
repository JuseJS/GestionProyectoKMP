package presentation.viewmodel

import data.store.UserStore
import domain.common.Result
import domain.usecase.auth.LoginParams
import domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.states.UiState

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val loginState: StateFlow<UiState<Unit>> = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        scope.launch {
            _loginState.value = UiState.Loading

            when (val result = loginUseCase(LoginParams(username, password))) {
                is Result.Success -> {
                    UserStore.setUser(result.data)
                    _loginState.value = UiState.Success(Unit)
                }
                is Result.Error -> {
                    _loginState.value = UiState.Error(result.exception.message ?: "Error desconocido")
                }
            }
        }
    }

    fun logout() {
        UserStore.clearUser()
    }
}