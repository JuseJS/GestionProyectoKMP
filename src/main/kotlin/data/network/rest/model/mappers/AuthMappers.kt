package data.network.rest.model.mappers

import data.network.rest.model.responses.AuthResponse
import domain.model.User

fun AuthResponse.toDomain(): User = User(
    id = id,
    user = user,
    name = name
)