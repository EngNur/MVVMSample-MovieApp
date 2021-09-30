package com.nur.moviesapp.common.di.details

import androidx.lifecycle.ViewModel
import com.nur.moviesapp.common.di.ViewModelKey
import com.nur.moviesapp.presentation.ui.details.MovieDetailsVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsVM::class)
    abstract fun bindsMovieDetailsViewModel(viewModel: MovieDetailsVM): ViewModel
}