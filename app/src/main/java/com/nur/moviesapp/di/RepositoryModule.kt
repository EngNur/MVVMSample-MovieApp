package com.nur.moviesapp.di

import android.app.Application
import com.nur.moviesapp.remote.repository.MoviesRepository
import com.nur.moviesapp.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    open fun provideMoviesRepository() : MoviesRepository {
        return MoviesRepository()
    }
}