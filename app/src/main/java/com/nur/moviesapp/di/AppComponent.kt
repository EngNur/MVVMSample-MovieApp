package com.nur.moviesapp.di

import com.nur.moviesapp.remote.APIService
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(service : APIService)
}