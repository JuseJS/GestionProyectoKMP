package domain.usecase.project

import data.network.rest.model.requests.CreateProjectRequest
import domain.common.Result
import domain.common.UseCase
import domain.model.Project
import domain.repository.ProjectRepository

class CreateProjectUseCase(
    private val repository: ProjectRepository
) : UseCase<CreateProjectRequest, Project>() {
    override suspend operator fun invoke(parameters: CreateProjectRequest): Result<Project> =
        repository.createProject(parameters)
}