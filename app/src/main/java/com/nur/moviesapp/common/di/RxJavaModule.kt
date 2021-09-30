package com.nur.moviesapp.common.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named


@Module
class RxJavaModule {

    @Provides
    @Named("SubscribeOn")
    fun provideSubscribeOnScheduler() : Scheduler{
        return Schedulers.io()
    }
    @Provides
    @Named("ObserveOn")
    fun provideObserveOnScheduler() : Scheduler{
        return AndroidSchedulers.mainThread()
    }

}