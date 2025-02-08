package di

import domain.usecase.auth.LoginUseCase
import org.koin.dsl.module
import presentation.viewmodel.LoginViewModel

val viewModelModule = module {
    factory { LoginUseCase(get()) }
    factory { LoginViewModel(get()) }
}