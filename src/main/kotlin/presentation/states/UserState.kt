package presentation.states

import domain.model.User

data class UserState(
    val currentUser: User? = null
)