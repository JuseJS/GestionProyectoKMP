package data.network.rest.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignProgrammerResponse(
    @SerialName("programmer") val programmer: Int,
    @SerialName("project") val project: Int,
    @SerialName("assignment_date") val assignmentDate: String
)