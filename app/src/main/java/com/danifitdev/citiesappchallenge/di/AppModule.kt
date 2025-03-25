package com.danifitdev.citiesappchallenge.di

import android.app.Application
import com.danifitdev.citiesappchallenge.MyApplication
import com.danifitdev.citiesappchallenge.core.data.networking.RetrofitProvider
import com.danifitdev.citiesappchallenge.core.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: Application): MyApplication {
        return app as MyApplication
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitProvider(retrofit: Retrofit): RetrofitProvider {
        return RetrofitProvider(retrofit)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object CoroutineModule {

        @Provides
        @Singleton
        fun provideApplicationScope(app: MyApplication): CoroutineScope {
            return app.applicationScope
        }
    }
}
