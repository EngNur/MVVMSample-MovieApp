package com.nur.moviesapp.data.remote

import com.nur.moviesapp.data.remote.model.MovieDetails
import com.nur.moviesapp.data.remote.model.MovieResponse
import retrofit2.http.Query
import retrofit2.http.GET
import io.reactivex.Single


interface APICall {

    @GET("?")
    fun getMovies(@Query(value = "s") searchTitle: String, @Query(value = "apiKey") apiKey: String): Single<MovieResponse>

    @GET("?plot=full")
    fun getMovieDetails(@Query(value = "i") ImdbId: String, @Query(value = "apiKey") apiKey: String): Single<MovieDetails>
}