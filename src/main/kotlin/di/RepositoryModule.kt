package di

import data.repository.AuthRepositoryImpl
import domain.repository.AuthRepository
import data.repository.ProjectRepositoryImpl
import domain.repository.ProjectRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    //single<ProjectRepository> { ProjectRepositoryImpl(get(), get()) }
}