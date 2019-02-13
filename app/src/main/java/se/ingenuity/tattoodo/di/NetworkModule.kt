package se.ingenuity.tattoodo.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import se.ingenuity.tattoodo.api.PostsApi
import javax.inject.Singleton

@Module
abstract class NetworkModule {
    @Module
    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideApi(): PostsApi {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            return Retrofit.Builder()
                .baseUrl("https://backend-api.tattoodo.com")
                .client(OkHttpClient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(PostsApi::class.java)
        }
    }
}