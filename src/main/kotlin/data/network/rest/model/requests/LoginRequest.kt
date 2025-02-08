package data.network.rest.model.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("user") val user: String,
    @SerialName("passwd") val passwd: String
)