package data.network.rest.model.mappers

import data.network.rest.model.responses.ProjectResponse
import domain.model.Project
import util.parseDateTime
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