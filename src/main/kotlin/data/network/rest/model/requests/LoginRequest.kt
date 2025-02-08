package data.network.rest.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val user: String,
    val passwd: String
)