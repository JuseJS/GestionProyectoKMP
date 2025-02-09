package presentation.states

import domain.model.Project

data class ProjectsState(
    val activeProjects: List<Project> = emptyList(),
    val managerActiveProjects: List<Project> = emptyList(),
    val managerEndedProjects: List<Project> = emptyList()
)