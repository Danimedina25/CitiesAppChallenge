package com.danifitdev.citiesappchallenge.core.data.networking

import com.google.gson.Gson
import com.google.gson.GsonBuilder

fun provideGson(): Gson = GsonBuilder().setLenient().create()