package di

import domain.usecase.auth.LoginUseCase
import domain.usecase.project.GetActiveProjectsUseCase
import domain.usecase.project.GetManagerProjectsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // Auth
    factory { LoginUseCase(get()) }

    // Projects
    factory { GetActiveProjectsUseCase(get()) }
    factory { GetManagerProjectsUseCase(get()) }
}