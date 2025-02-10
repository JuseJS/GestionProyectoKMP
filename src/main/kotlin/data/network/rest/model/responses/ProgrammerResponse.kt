package data.network.rest.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgrammerResponse(
    @SerialName("id") val id: Int,
    @SerialName("nombre") val name: String
)