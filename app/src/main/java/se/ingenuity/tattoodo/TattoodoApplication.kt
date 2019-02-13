package se.ingenuity.tattoodo

import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerApplication
import se.ingenuity.tattoodo.di.AppComponent
import se.ingenuity.tattoodo.di.DaggerAppComponent
import se.ingenuity.tattoodo.di.databinding.DaggerBindingComponent

class TattoodoApplication : DaggerApplication() {
    private lateinit var appComponent : AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder().application(this).build()

        val bindingComponent = DaggerBindingComponent.builder().appComponent(appComponent).build()
        DataBindingUtil.setDefaultComponent(bindingComponent)

        super.onCreate()
    }

    override fun applicationInjector() = appComponent
}