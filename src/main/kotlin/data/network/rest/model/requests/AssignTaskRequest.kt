package data.network.rest.model.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignTaskRequest(
    @SerialName("programador") val programmer: Int,
    @SerialName("tarea") val task: Int
)