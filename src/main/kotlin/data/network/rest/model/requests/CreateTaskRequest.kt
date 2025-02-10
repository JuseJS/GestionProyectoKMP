package data.network.rest.model.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    @SerialName("gestor") val manager: Int,
    @SerialName("nombre") val name: String,
    @SerialName("descripcion") val description: String,
    @SerialName("estimacion") val estimation: Int,
    @SerialName("proyecto") val project: Int,
    @SerialName("programador") val programmer: Int?
)