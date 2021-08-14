package com.nur.moviesapp.di

import com.nur.moviesapp.ui.details.MovieDetailsVM
import com.nur.moviesapp.ui.main.MoviesVM
import dagger.Component

@Component(modules = [RepositoryModule::class, AppModule::class])
interface RepositoryComponent {
    fun inject(moviesVM: MoviesVM )
    fun inject(moviesVM: MovieDetailsVM )
}