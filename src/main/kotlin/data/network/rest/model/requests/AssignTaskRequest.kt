package data.network.rest.model.requests

data class AssignTaskRequest(
    val taskId: Int,
    val programmerId: Int
)