package se.ingenuity.tattoodo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import se.ingenuity.tattoodo.feature.detail.DetailActivity
import se.ingenuity.tattoodo.feature.main.MainActivity

@Module
interface ActivitiesModule {
    @ContributesAndroidInjector(modules = [MainActivity.Module::class])
    fun contributeMain(): MainActivity

    @ContributesAndroidInjector(modules = [DetailActivity.Module::class])
    fun contributeDetails(): DetailActivity
}