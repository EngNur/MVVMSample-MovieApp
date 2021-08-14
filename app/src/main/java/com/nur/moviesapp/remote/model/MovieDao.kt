package com.nur.moviesapp.remote.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    suspend fun insertAll(vararg  movies : Movie): List<Long>

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies() : List<Movie>

    @Query("SELECT * FROM movie WHERE uuid = :movieId")
    suspend fun getMovie(movieId : Int): Movie

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()
}