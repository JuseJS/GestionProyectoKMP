package domain.model

import java.time.LocalDateTime

data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val creationDate: LocalDateTime,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val clientId: Int
)