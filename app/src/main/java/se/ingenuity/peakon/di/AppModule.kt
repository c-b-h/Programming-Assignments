package se.ingenuity.peakon.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module(includes = [ActivitiesModule::class, LifecycleModule::class, NetworkModule::class])
abstract class AppModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun providePicasso(): Picasso = Picasso.get()
    }
}