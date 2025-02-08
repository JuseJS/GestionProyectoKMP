package data.network.rest.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class EmployerResponse(
    val name: String,
    val role: String
)