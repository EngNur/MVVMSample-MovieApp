package com.nur.moviesapp

import com.nur.moviesapp.di.AppModule
import com.nur.moviesapp.remote.APIService

class AppModuleTest(val mockService : APIService) : AppModule() {

    override fun provideApiService(): APIService {
        return mockService
    }

}