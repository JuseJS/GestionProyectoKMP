package domain.usecase.project

import domain.common.Result
import domain.common.UseCase
import domain.model.Project
import domain.repository.ProjectRepository

class GetActiveProjectsUseCase(
    private val repository: ProjectRepository
) : UseCase<Unit, List<Project>>() {
    override suspend operator fun invoke(parameters: Unit): Result<List<Project>> =
        repository.getActiveProjects()
}