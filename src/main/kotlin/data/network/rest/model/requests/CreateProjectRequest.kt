package data.network.rest.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectRequest(
    val name: String,
    val description: String,
    val startDate: String,
    val client: Int
)