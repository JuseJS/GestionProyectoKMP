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
            projectRepository = get(),
            taskRepository = get()
        )
    }

}