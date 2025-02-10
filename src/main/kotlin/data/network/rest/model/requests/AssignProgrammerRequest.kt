package data.network.rest.model.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignProgrammerRequest(
    @SerialName("programador") val programmer: Int,
    @SerialName("proyecto") val project: Int,
)