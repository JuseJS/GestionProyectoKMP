package di

import org.koin.dsl.module
import presentation.viewmodel.LoginViewModel
import presentation.viewmodel.ProjectViewModel
import presentation.viewmodel.ProjectDetailViewModel
import presentation.viewmodel.WelcomeViewModel

val viewModelModule = module {
    factory { LoginViewModel(get()) }

    factory {
        WelcomeViewModel(
            projectRepository = get()
        )
    }

    factory {
        ProjectDetailViewModel(
            taskRepository = get(),
            projectRepository = get()
        )
    }

    factory {
        ProjectViewModel(
            projectRepository = get()
        )
    }
}