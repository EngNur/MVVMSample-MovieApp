package com.nur.moviesapp.data.repository

import android.util.Log
import com.nur.moviesapp.data.local.sharedpreferences.AppConfig
import com.nur.moviesapp.data.remote.APIService
import com.nur.moviesapp.data.local.sqlite.MovieDao
import com.nur.moviesapp.data.local.sqlite.model.MovieEntity
import com.nur.moviesapp.data.mapper.fromLocalToDomain
import com.nur.moviesapp.data.mapper.fromRemoteToDomain
import com.nur.moviesapp.data.mapper.fromRemoteToLocal
import com.nur.moviesapp.data.remote.model.MovieDetails
import com.nur.moviesapp.domain.model.MovieDomain
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Named

class MoviesRepository @Inject constructor(
    @Named("SubscribeOn") val subscribeOn: Scheduler,
    @Named("ObserveOn") val observeOn: Scheduler,
    var apiService: APIService,
    var movieDao: MovieDao,
    var prefHelper: AppConfig
) {

    fun getMovies(): Single<List<MovieDomain>> {
        return if (prefHelper.isMovieListExpired()) {
            getMoviesFromServer()
        } else
            getMoviesFromDB()
    }

    private fun getMoviesFromServer(): Single<List<MovieDomain>> {
        Log.d("data resource","getMoviesFromServer")
        return apiService.getMovies("love")
            .map { response ->
                storeMoviesLocally(response.Search.map {fromRemoteToLocal(it)} )
                //storeMoviesLocally(response.Search)
                response.Search.map {fromRemoteToDomain(it) }
            }
            .subscribeOn(subscribeOn)
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getMoviesFromDB(): Single<List<MovieDomain>> {
        Log.d("data resource","getMoviesFromDB")
        return Single.fromCallable(Callable {
            movieDao.getAllMovies().map { fromLocalToDomain(it) }
        }).subscribeOn(subscribeOn)
            .observeOn(observeOn)
    }

    private fun storeMoviesLocally(list: List<MovieEntity>) {
        movieDao.deleteAllMovies()
        movieDao.insertAll(*list.toTypedArray())
        prefHelper.saveUpdateTime(System.nanoTime())
    }



    fun getMovieDetails(id: String): Single<MovieDetails> {
        return apiService.getMovieDetails(id)
            .subscribeOn(subscribeOn)
            .observeOn(observeOn)
    }
}