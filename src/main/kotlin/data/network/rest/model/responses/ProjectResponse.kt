package data.network.rest.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse(
    @SerialName("id") val id: Int,
    @SerialName("nombre") val name: String,
    @SerialName("descripcion") val description: String,
    @SerialName("fecha_creacion") val creationDate: String,
    @SerialName("fecha_inicio") val startDate: String,
    @SerialName("fecha_finalizacion") val endDate: String?,
    @SerialName("cliente_nombre") val clientName: String
)