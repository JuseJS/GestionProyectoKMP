package di

import data.repository.AuthRepositoryImpl
import domain.repository.AuthRepository
import data.repository.ProjectRepositoryImpl
import data.repository.TaskRepositoryImpl
import domain.repository.ProjectRepository
import domain.repository.TaskRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    //single<ProjectRepository> { ProjectRepositoryImpl(get(), get()) }
}