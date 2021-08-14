package com.nur.moviesapp.ui.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.nur.moviesapp.base.BaseViewModel
import com.nur.moviesapp.di.DaggerRepositoryComponent
import com.nur.moviesapp.di.DaggerViewModelComponent
import com.nur.moviesapp.remote.model.Movie
import com.nur.moviesapp.remote.model.MovieDataBase
import com.nur.moviesapp.remote.model.MovieResponse
import com.nur.moviesapp.remote.repository.MoviesRepository
import com.nur.moviesapp.util.NotificationHelper
import com.nur.moviesapp.util.SharedPreferencesHelper
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

class MoviesVM(application : Application) : BaseViewModel(application){

    val movies by lazy { MutableLiveData<List<Movie>>() }


     var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L//5 minutes in nano second

    @Inject
    lateinit var movieRepository : MoviesRepository

   fun inject() {
        DaggerRepositoryComponent.create().inject(this)
   }
    fun refresh(){
        val updateTime = prefHelper.getUpdateTime()
        inject()
        checkCacheDuration()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getMoviesFromDB()
        }else
            getMovies()
    }


    private fun checkCacheDuration(){
        val cachePref = prefHelper.getCacheDuration()
        try {
            val cachePreInt = cachePref?.toInt() ?: 5 * 60
            refreshTime = cachePreInt.times( 1000 * 1000 * 1000L)
        }catch (e : NumberFormatException){
            e.printStackTrace()
        }

    }

     fun getMoviesByPassDB(){
        getMovies()
    }
    private fun getMoviesFromDB(){
       loading.value = true
       launch {
           val movies = MovieDataBase(getApplication()).movieDao().getAllMovies()
           onRetrieveMoviesListSuccess(movies)
           Toast.makeText(getApplication(),"Retrieved from data base",Toast.LENGTH_LONG).show()
       }
    }

    private fun getMovies(){
       disposable.add(
           movieRepository.getMovies()
               .subscribeWith(object : DisposableObserver<MovieResponse>(){
                   override fun onNext(t: MovieResponse) {
                       storeMoviesLocally(t.Search)
                       Toast.makeText(getApplication(),"Retrieved from server",Toast.LENGTH_LONG).show()
                       NotificationHelper(getApplication()).createNotification()
                   }
                   override fun onError(e: Throwable) {
                       onRetrieveMoviesListError()
                   }
                   override fun onComplete() {}
               })
       )
    }

    private fun storeMoviesLocally(list : List<Movie>){
        launch {
            val dao  = MovieDataBase(getApplication()).movieDao()
            dao.deleteAllMovies()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            onRetrieveMoviesListSuccess(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())

    }

    private fun onRetrieveMoviesListSuccess(moviesList:List<Movie>){
        movies.value  = moviesList
        loading.value = false
        loadError.value = false
    }

     override fun onRetrieveMoviesListError(){
        super.onRetrieveMoviesListError()
        movies.value = null
    }


}