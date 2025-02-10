package data.network.rest.model.mappers

import data.network.rest.model.responses.ProgrammerResponse
import domain.model.Programmer

fun ProgrammerResponse.toDomain(): Programmer = Programmer(
    id = id,
    name = name
)