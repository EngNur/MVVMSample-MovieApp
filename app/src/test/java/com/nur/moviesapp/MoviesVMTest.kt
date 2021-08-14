package com.nur.moviesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nur.moviesapp.di.DaggerViewModelComponent
import com.nur.moviesapp.remote.APIService
import com.nur.moviesapp.remote.model.Movie
import com.nur.moviesapp.remote.model.MovieResponse
import com.nur.moviesapp.ui.main.MoviesVM
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import org.junit.Rule
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class MoviesVMTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: APIService

    var moviesVM = MoviesVM(true)


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
//       DaggerViewModelComponent.builder()
//                .apiModule(ApiModuleTest(apiService))
//                .build()
//                .inject(moviesVM)

    }

    @Test
    fun getMoviesFailure(){
        val testObservable = Observable.error<MovieResponse>(Throwable())
        Mockito.`when`(apiService.getMovies("love")).thenReturn(testObservable)
        moviesVM.refresh()
        Assert.assertEquals(null,moviesVM.movies.value)
        Assert.assertEquals(true,moviesVM.loadError.value)
        Assert.assertEquals(false,moviesVM.loading.value)

    }
    @Test
    fun getMoviesSuccess(){
      val movie  = Movie("test movie",null,null,null,null)
        val moviesList = listOf(movie)
        val testObservable :  Observable<MovieResponse> = Observable.just(MovieResponse(moviesList,"True"))

        Mockito.`when`(apiService.getMovies("love")).thenReturn(testObservable)

        moviesVM.refresh()
        Assert.assertEquals(1,moviesVM.movies.value?.size)
        Assert.assertEquals(false,moviesVM.loadError.value)
        Assert.assertEquals(false,moviesVM.loading.value)

    }

    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}