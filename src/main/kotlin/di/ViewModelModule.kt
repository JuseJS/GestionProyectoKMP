package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.viewmodel.LoginViewModel
import presentation.viewmodel.WelcomeViewModel

val viewModelModule = module {
    factoryOf(::LoginViewModel)
    factoryOf(::WelcomeViewModel)
}