package com.nur.moviesapp.common.di

import android.app.Application
import com.nur.moviesapp.BASE_URL
import com.nur.moviesapp.data.remote.APICall
import com.nur.moviesapp.data.remote.APIService
import com.nur.moviesapp.data.local.sharedpreferences.AppConfig
import com.nur.moviesapp.data.local.sqlite.MovieDao
import com.nur.moviesapp.data.local.sqlite.MovieDataBase
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class AppModule() {

    @Provides
    fun provideApi(): APICall {
      return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(APICall::class.java)
    }

    @Provides
   open fun provideApiService(api: APICall) : APIService {
        return APIService(api)
    }

    @Provides
    open fun provideMoviesDao(application : Application) : MovieDao {
        return MovieDataBase(application).movieDao()
    }

    @Provides
    open fun provideSharedPreferenceHelper(application : Application) : AppConfig {
        return AppConfig(application)
    }
}