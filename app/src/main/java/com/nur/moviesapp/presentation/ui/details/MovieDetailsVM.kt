package com.nur.moviesapp.presentation.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nur.moviesapp.presentation.base.BaseViewModel
import com.nur.moviesapp.data.remote.model.MovieDetails
import com.nur.moviesapp.data.repository.MoviesRepository
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieDetailsVM  @Inject constructor(var moviesRepository: MoviesRepository)  : BaseViewModel( ){
    val movieDetails by lazy { MutableLiveData<MovieDetails>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val loadError by lazy { MutableLiveData<Boolean>() }

     fun getMovieDetails(id : String){
        disposable.add(
                moviesRepository.getMovieDetails(id)
                .subscribeWith(object : DisposableSingleObserver<MovieDetails>(){
                    override fun onSuccess(t: MovieDetails) {
                        onRetrieveMovieDetailsSuccess(t)
                    }
                    override fun onError(e: Throwable) {
                        Log.e("onError", e.message.toString())
                        onRetrieveMoviesListError()
                    }
                })
        )
    }

    private fun onRetrieveMovieDetailsSuccess(details: MovieDetails){
        movieDetails.value  = details
        loading.value = false
        loadError.value = false
    }

     fun onRetrieveMoviesListError(){
        loading.value = false
        loadError.value = true
    }

}