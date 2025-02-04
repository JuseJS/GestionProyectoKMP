package domain.model

import java.time.LocalDate

data class TaskData(
    val id: Int,
    val name: String,
    val description: String,
    val estimatedHours: Int,
    val creationDate: LocalDate,
    val projectId: Int,
    val assignedDeveloper: String? = null,
    val completionDate: LocalDate? = null
)