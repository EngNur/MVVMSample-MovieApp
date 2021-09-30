package com.nur.moviesapp.presentation.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    val disposable = CompositeDisposable()
//
//    private val job = Job()
//    override val coroutineContext: CoroutineContext
//        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
      //  job.cancel()
        disposable.clear()
    }


}