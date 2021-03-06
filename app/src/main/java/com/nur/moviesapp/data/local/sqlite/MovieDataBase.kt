package com.nur.moviesapp.data.local.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nur.moviesapp.data.local.sqlite.model.MovieEntity

@Database(entities = arrayOf(MovieEntity::class),version = 2)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun  movieDao(): MovieDao

    companion object{
        @Volatile private  var instance: MovieDataBase? = null
        private val LOCK = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance ?: buildDataBase(context).also{
                instance = it
            }
        }

        private fun buildDataBase(context : Context)= Room.databaseBuilder(
            context.applicationContext,
            MovieDataBase::class.java,
            "moviedatabase"
        ).fallbackToDestructiveMigration()
            .build()
    }

}