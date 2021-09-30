package com.nur.moviesapp.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit


class AppConfig {

    companion object {
        private const val PREF_TIME = "Pref Time"
        private var prefs: SharedPreferences? = null
        private var refreshTime = 0L

        @Volatile private var instance: AppConfig? = null
        private val LOCK = Any()
        operator fun invoke(context : Context): AppConfig = instance ?: synchronized(
            LOCK
        ){
            instance ?: buildHelper(context).also{
                instance = it
            }
        }

        private fun  buildHelper(context: Context) : AppConfig {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return AppConfig()
        }
    }

    fun saveUpdateTime(time : Long){
        prefs?.edit(commit = true){putLong(PREF_TIME,time * 60)}
    }

    fun isMovieListExpired():Boolean {
        checkCacheDuration()
        val updateTime = getUpdateTime()
        return !(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime)
    }

    private fun checkCacheDuration() {
        val cachePref = getCacheDuration()
        try {
            val cachePreInt = cachePref?.toInt() ?: 5 * 60
            refreshTime = cachePreInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    private fun getUpdateTime() = prefs?.getLong(PREF_TIME,0)

    private fun getCacheDuration() = prefs?.getString("pref_cache_duration","")
}