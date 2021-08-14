package com.nur.moviesapp.remote

import com.nur.moviesapp.remote.model.MovieDetails
import com.nur.moviesapp.remote.model.MovieResponse
import retrofit2.http.Query
import retrofit2.http.GET
import io.reactivex.Observable


interface APICall {

    @GET("?")
    fun getMovies(@Query(value = "s") searchTitle: String, @Query(value = "apiKey") apiKey: String): Observable<MovieResponse>

    @GET("?plot=full")
    fun getMovieDetails(@Query(value = "i") ImdbId: String, @Query(value = "apiKey") apiKey: String): Observable<MovieDetails>
}