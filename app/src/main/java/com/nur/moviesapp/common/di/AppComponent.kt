package com.nur.moviesapp.common.di

import android.app.Application
import com.nur.moviesapp.MyApplication
import com.nur.moviesapp.presentation.ui.movieList.MovieListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class
    ,FragmentBuilderModule::class
    , AppModule::class
    ,ViewModelModule::class
    ,RxJavaModule::class
  ])
interface AppComponent : AndroidInjector<MyApplication>{
    @Component.Builder
    interface  Builder{

        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }



}