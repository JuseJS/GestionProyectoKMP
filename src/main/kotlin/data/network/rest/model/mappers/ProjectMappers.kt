package data.network.rest.model.mappers

import data.network.rest.model.responses.ProjectResponse
import domain.model.Project
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ProjectResponse.toDomain(): Project = Project(
    id = id,
    name = name,
    description = description,
    creationDate = parseDateTime(creationDate),
    startDate = parseDateTime(startDate),
    endDate = endDate?.let { parseDateTime(it) },
    clientId = clientId
)

private fun parseDateTime(dateStr: String): LocalDateTime {
    // Formato para parsear fechas como "Thu, 09 Jan 2025 09:00:09 GMT"
    val formatter = DateTimeFormatter.ofPattern(
        "EEE, dd MMM yyyy HH:mm:ss z",
        Locale.ENGLISH
    )

    return ZonedDateTime.parse(dateStr, formatter)
        .toLocalDateTime()
}