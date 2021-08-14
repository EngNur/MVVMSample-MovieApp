package com.nur.moviesapp.remote

import com.nur.moviesapp.API_KEY
import com.nur.moviesapp.di.DaggerAppComponent
import com.nur.moviesapp.remote.model.MovieDetails
import com.nur.moviesapp.remote.model.MovieResponse
import io.reactivex.Observable
import javax.inject.Inject

class APIService {

    @Inject
    lateinit var api: APICall
    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getMovies(searchKey : String) : Observable<MovieResponse> {
        return api.getMovies(searchKey,API_KEY)
    }

    fun getMovieDetails(id : String) : Observable<MovieDetails> {
        return api.getMovieDetails(id,API_KEY)
    }
}