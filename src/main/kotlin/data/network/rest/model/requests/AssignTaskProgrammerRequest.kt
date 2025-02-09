package data.network.rest.model.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignTaskProgrammerRequest(
    @SerialName("programador") val programmer: Int,
    @SerialName("tarea") val task: Int
)