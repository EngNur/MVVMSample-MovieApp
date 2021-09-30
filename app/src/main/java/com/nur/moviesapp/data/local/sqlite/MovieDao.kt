package com.nur.moviesapp.data.local.sqlite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nur.moviesapp.data.local.sqlite.model.MovieEntity

@Dao
interface MovieDao {
    @Insert
    fun insertAll(vararg movies: MovieEntity)

    @Query("SELECT * FROM movieentity")
    fun getAllMovies(): List<MovieEntity>

//    @Query("SELECT * FROM movie")
//    fun getAllMovies2(): Single<List<Movie>>

    @Query("SELECT * FROM movieentity WHERE uuid = :movieId")
    fun getMovie(movieId: Int): MovieEntity

    @Query("DELETE FROM movieentity")
    fun deleteAllMovies()
}