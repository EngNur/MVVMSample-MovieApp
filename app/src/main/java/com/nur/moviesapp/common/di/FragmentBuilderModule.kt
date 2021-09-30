package com.nur.moviesapp.common.di

import com.nur.moviesapp.common.di.details.MovieDetailsViewModelModule
import com.nur.moviesapp.common.di.movielist.MovieListViewModelModule
import com.nur.moviesapp.presentation.ui.details.MovieDetailsFragment
import com.nur.moviesapp.presentation.ui.movieList.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [MovieListViewModelModule::class])
    abstract fun  contributeMovieListFragment():MovieListFragment

    @ContributesAndroidInjector(modules = [MovieDetailsViewModelModule::class])
    abstract fun  contributeMovieDetailsFragment():MovieDetailsFragment

}