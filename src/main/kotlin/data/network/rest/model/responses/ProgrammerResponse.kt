package data.network.rest.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgrammerResponse(
    @SerialName("id") val id: Int,
    @SerialName("empleado") val employerId: Int,
    @SerialName("nombre") val name: String,
    @SerialName("email") val email: String,
    @SerialName("sueldo_hora") val hourlyWage: Int
)