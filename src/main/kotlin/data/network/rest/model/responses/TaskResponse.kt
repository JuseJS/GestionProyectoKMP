package data.network.rest.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    @SerialName("id") val id: Int,
    @SerialName("nombre") val name: String,
    @SerialName("descripcion") val description: String,
    @SerialName("estimacion") val estimation: Int,
    @SerialName("fecha_creacion") val creationDate: String,
    @SerialName("fecha_finalizacion") val endDate: String,
    @SerialName("proyecto") val projectId: Int,
    @SerialName("programador") val programmerId: Int
)