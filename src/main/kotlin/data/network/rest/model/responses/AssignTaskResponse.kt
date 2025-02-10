package data.network.rest.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignTaskResponse(
    @SerialName("programmer") val programmer: Int,
    @SerialName("task") val task: Int
)