package com.nur.moviesapp


import com.nur.moviesapp.common.di.AppComponent
import com.nur.moviesapp.common.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApplication : DaggerApplication() {
    lateinit  var component: AppComponent

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder().application(this).build()
    }

    fun getAppComponent() : AppComponent{
        return component
    }
}