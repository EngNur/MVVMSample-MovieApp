package com.nur.moviesapp.data.mapper

import com.nur.moviesapp.data.local.sqlite.model.MovieEntity
import com.nur.moviesapp.data.remote.model.MovieRemote
import com.nur.moviesapp.domain.model.MovieDomain


fun fromRemoteToLocal(movieRemote: MovieRemote): MovieEntity{
    return MovieEntity(movieRemote.Title,movieRemote.imdbID,movieRemote.Poster)
}

fun fromRemoteToDomain(movieRemote: MovieRemote): MovieDomain{
   var movieDomain = MovieDomain(movieRemote.Title,movieRemote.imdbID,movieRemote.Poster)
    movieDomain.year = movieRemote.Year ?: ""
    return movieDomain
}

fun fromLocalToDomain(movieEntity: MovieEntity): MovieDomain{
    return MovieDomain(movieEntity.Title,movieEntity.imdbID,movieEntity.Poster)
}