package di

import data.cache.LocalCache
import data.repository.ProjectRepositoryImpl
import domain.repository.ProjectRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProjectRepository> { ProjectRepositoryImpl(get(), get()) }
    single { LocalCache() }
}