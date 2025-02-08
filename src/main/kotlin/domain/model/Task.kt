package domain.model

import java.time.LocalDateTime

data class Task(
    val id: String,
    val name: String,
    val description: String,
    val estimation: Int,
    val creationDate: LocalDateTime,
    val projectId: String,
    val programmerId: String?
)