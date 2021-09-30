package com.nur.moviesapp.data.remote

import com.nur.moviesapp.API_KEY
import com.nur.moviesapp.data.remote.model.MovieDetails
import com.nur.moviesapp.data.remote.model.MovieResponse
import io.reactivex.Single
import javax.inject.Inject

class APIService @Inject constructor( var api: APICall){

    fun getMovies(searchKey : String) : Single<MovieResponse> {
        return api.getMovies(searchKey,API_KEY)
    }

    fun getMovieDetails(id : String) : Single<MovieDetails> {
        return api.getMovieDetails(id,API_KEY)
    }
}