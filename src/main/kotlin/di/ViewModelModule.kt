package di

import org.koin.dsl.module
import presentation.viewmodel.LoginViewModel
import presentation.viewmodel.WelcomeViewModel

val viewModelModule = module {
    factory { LoginViewModel(get()) }

    factory {
        WelcomeViewModel(
            getActiveProjectsUseCase = get(),
            getManagerProjectsUseCase = get()
        )
    }
}