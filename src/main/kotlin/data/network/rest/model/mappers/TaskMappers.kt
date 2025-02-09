package data.network.rest.model.mappers

import data.network.rest.model.responses.TaskResponse
import domain.model.Task
import util.parseDateTime

fun TaskResponse.toDomain(): Task = Task(
    id = id,
    name = name,
    description = description,
    estimation = estimation,
    creationDate = parseDateTime(creationDate),
    endDate = endDate?.let { parseDateTime(it) },
    projectId = projectId,
    programmerId = programmerId,
)