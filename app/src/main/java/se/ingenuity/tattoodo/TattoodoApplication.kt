package se.ingenuity.tattoodo

import dagger.android.support.DaggerApplication
import se.ingenuity.tattoodo.di.AppComponent
import se.ingenuity.tattoodo.di.DaggerAppComponent

class TattoodoApplication : DaggerApplication() {
    private lateinit var appComponent : AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder().application(this).build()

        super.onCreate()
    }

    override fun applicationInjector() = appComponent
}