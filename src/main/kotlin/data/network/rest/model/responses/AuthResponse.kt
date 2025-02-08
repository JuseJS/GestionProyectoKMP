package data.network.rest.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val id: Int,
    val user: String,
    val name: String
)