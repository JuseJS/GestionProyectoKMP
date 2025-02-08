package domain.model

import java.time.LocalDateTime

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val creationDate: LocalDateTime,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val clientName: String
)