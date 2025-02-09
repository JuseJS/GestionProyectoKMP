package data.network.rest.model.mappers

import data.network.rest.model.responses.ProjectResponse
import domain.model.Project
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun ProjectResponse.toDomain(): Project = Project(
    id = id,
    name = name,
    description = description,
    creationDate = parseDateTime(creationDate),
    startDate = parseDateTime(startDate),
    endDate = endDate?.let { parseDateTime(it) },
    clientName = clientName
)

private fun parseDateTime(dateStr: String): LocalDateTime {
    return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME)
}