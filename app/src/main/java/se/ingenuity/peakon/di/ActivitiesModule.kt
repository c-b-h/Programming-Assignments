package se.ingenuity.peakon.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import se.ingenuity.peakon.feature.search.SearchActivity

@Module
interface ActivitiesModule {
    @ContributesAndroidInjector(modules = [SearchActivity.Module::class])
    fun contributeSearch(): SearchActivity
}