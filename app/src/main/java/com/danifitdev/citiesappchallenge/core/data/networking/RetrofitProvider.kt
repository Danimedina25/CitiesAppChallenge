package com.danifitdev.citiesappchallenge.core.data.networking

import retrofit2.Retrofit

class RetrofitProvider(private val retrofit: Retrofit) {

    fun <T> provideApiService(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}
