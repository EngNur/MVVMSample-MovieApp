package com.nur.moviesapp.presentation.ui.movieList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nur.moviesapp.presentation.base.BaseViewModel
import com.nur.moviesapp.data.repository.MoviesRepository
import com.nur.moviesapp.domain.model.MovieDomain
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieListVM @Inject constructor(var movieRepository: MoviesRepository) : BaseViewModel() {
    val loading by lazy { MutableLiveData<Boolean>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val movies by lazy { MutableLiveData<List<MovieDomain>>() }

    fun refresh() {
        getMovies()
    }

    private fun getMovies() {
        loading.value = true
        disposable.add(
            movieRepository.getMovies()
                .subscribeWith(object : DisposableSingleObserver<List<MovieDomain>>() {
                    override fun onError(e: Throwable) {
                        Log.e("errormessage",e.message+"")
                        onRetrieveMoviesListError()
                    }
                    override fun onSuccess(t: List<MovieDomain>) {
                        onRetrieveMoviesListSuccess(t)
                    }
                })
        )
    }

    private fun onRetrieveMoviesListSuccess(moviesList: List<MovieDomain>) {
        movies.value = moviesList
        loading.value = false
        loadError.value = false
    }

     fun onRetrieveMoviesListError() {
        loading.value = false
        loadError.value = true
    }


}