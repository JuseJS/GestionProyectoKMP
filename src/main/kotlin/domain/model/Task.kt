package domain.model

import java.time.LocalDateTime

data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val estimation: Int,
    val creationDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val projectId: Int,
    val programmerId: Int?
)