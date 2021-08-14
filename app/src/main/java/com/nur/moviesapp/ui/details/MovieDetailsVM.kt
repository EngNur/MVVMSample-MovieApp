package com.nur.moviesapp.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nur.moviesapp.base.BaseViewModel
import com.nur.moviesapp.di.DaggerRepositoryComponent
import com.nur.moviesapp.remote.model.MovieDetails
import com.nur.moviesapp.remote.repository.MoviesRepository
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MovieDetailsVM(application : Application) : BaseViewModel(application){
    val movieDetails by lazy { MutableLiveData<MovieDetails>() }

    @Inject
    lateinit var moviesRepository: MoviesRepository

    init {
        DaggerRepositoryComponent.create().inject(this)
    }

     fun getMovieDetails(id : String){
        disposable.add(
                moviesRepository.getMovieDetails(id)
                .subscribeWith(object : DisposableObserver<MovieDetails>(){
                    override fun onNext(t: MovieDetails) {
                        onRetrieveMovieDetailsSuccess(t)
                    }
                    override fun onError(e: Throwable) {
                        Log.e("onError", e.message.toString())
                        onRetrieveMoviesListError()
                    }
                    override fun onComplete() {}

                })
        )
    }

    private fun onRetrieveMovieDetailsSuccess(details: MovieDetails){
        movieDetails.value  = details
        loading.value = false
        loadError.value = false
    }

    override fun onRetrieveMoviesListError(){
        super.onRetrieveMoviesListError()
        movieDetails.value = null
    }

}