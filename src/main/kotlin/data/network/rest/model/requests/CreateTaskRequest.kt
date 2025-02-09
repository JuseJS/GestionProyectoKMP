package data.network.rest.model.requests

data class CreateTaskRequest(
    val name: String,
    val description: String,
    val estimation: Int,
    val projectId: Int,
    val programmerId: Int?
)