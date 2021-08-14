package com.nur.moviesapp.di

import android.app.Application
import com.nur.moviesapp.BASE_URL
import com.nur.moviesapp.remote.APICall
import com.nur.moviesapp.remote.APIService
import com.nur.moviesapp.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class AppModule {

    @Provides
    fun provideApi(): APICall{
      return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(APICall::class.java)
    }

    @Provides
   open fun provideApiService() : APIService{
        return APIService()
    }

    @Provides
    open fun provideSharedPreferenceHelper(application : Application) : SharedPreferencesHelper {
        return SharedPreferencesHelper(application)
    }
}