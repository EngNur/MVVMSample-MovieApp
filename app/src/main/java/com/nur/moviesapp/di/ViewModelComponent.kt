package com.nur.moviesapp.di

import com.nur.moviesapp.remote.repository.MoviesRepository
import com.nur.moviesapp.util.SharedPreferencesHelper
import dagger.Component

@Component(modules = [AppModule::class])
interface ViewModelComponent {
    fun inject(moviesRepository: MoviesRepository)
}