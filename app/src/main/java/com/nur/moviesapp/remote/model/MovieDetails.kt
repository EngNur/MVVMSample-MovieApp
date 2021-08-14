package com.nur.moviesapp.remote.model

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

data class SMSInfo(var to : String, var text : String, var imageUrl : String?)