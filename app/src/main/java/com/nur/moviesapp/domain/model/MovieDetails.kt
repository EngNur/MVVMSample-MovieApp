package com.nur.moviesapp.data.remote.model

data class MovieDetails (
    val Title: String,
    val BoxOffice: String,
    val Released: String,
    val Genre: String,
    val Director: String,
    val Actors: String,
    val Plot: String,
    val Poster: String
)

data class MoviePalette(var color : Int)

