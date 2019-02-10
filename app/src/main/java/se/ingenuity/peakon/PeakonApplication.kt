package se.ingenuity.peakon

import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerApplication
import se.ingenuity.peakon.di.AppComponent
import se.ingenuity.peakon.di.DaggerAppComponent
import se.ingenuity.peakon.di.databinding.DaggerBindingComponent

class PeakonApplication : DaggerApplication() {
    private lateinit var appComponent : AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.create()

        val bindingComponent = DaggerBindingComponent.builder().appComponent(appComponent).build()
        DataBindingUtil.setDefaultComponent(bindingComponent)

        super.onCreate()
    }

    override fun applicationInjector() = appComponent
}