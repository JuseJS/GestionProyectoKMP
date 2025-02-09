package data.network.rest.model.requests

data class AssignProgrammerRequest(
    val programmerId: Int,
    val projectId: Int
)