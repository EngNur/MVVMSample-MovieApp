package com.nur.moviesapp.data.local.sqlite.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @ColumnInfo(name = "movie_title")
    val Title: String?,
    @ColumnInfo(name = "movie_id")
    val imdbID:String?,
    @ColumnInfo(name = "movie_poster")
    val Poster:String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}