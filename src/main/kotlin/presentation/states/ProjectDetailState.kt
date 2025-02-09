package presentation.states

import domain.model.Project
import domain.model.Task

data class ProjectDetailState(
    val project: Project,
    val tasks: List<Task> = emptyList()
)