package com.nur.moviesapp.remote.repository

import com.nur.moviesapp.di.DaggerViewModelComponent
import com.nur.moviesapp.remote.APIService
import com.nur.moviesapp.remote.model.MovieDetails
import com.nur.moviesapp.remote.model.MovieResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesRepository {

    @Inject
    lateinit var  apiService : APIService


    init {
        DaggerViewModelComponent.create().inject(this)
    }

    fun getMovies():  Observable<MovieResponse> {
        return apiService.getMovies("love")
                .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovieDetails(id : String):   Observable<MovieDetails>  {
        return apiService.getMovieDetails(id)
                .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
    }
}