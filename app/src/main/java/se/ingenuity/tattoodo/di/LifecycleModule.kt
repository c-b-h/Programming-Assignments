package se.ingenuity.tattoodo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.Multibinds
import se.ingenuity.tattoodo.lifecycle.ViewModelFactory
import javax.inject.Singleton

@Module
interface LifecycleModule {
    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Multibinds
    @Singleton
    fun viewModels(): MutableMap<Class<out ViewModel>, ViewModel>
}