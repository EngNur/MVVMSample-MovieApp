package com.nur.moviesapp.common.di.movielist

import androidx.lifecycle.ViewModel
import com.nur.moviesapp.common.di.ViewModelKey
import com.nur.moviesapp.presentation.ui.movieList.MovieListVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListVM::class)
    abstract fun bindsMovieListViewModel(viewModel: MovieListVM): ViewModel

}