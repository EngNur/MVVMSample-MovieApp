package com.nur.moviesapp.common.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nur.moviesapp.presentation.ui.movieList.MovieListVM
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

         @Binds
        abstract fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
    }