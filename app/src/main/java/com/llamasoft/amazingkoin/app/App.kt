package com.llamasoft.amazingkoin.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.llamasoft.amazingkoin.app.di.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(
                networkModule, viewModelModule, repositoryModule, databaseModule, applicationModule
            ))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun updateApiUrl(url: String) {
        // This is the "trick"
        var retrofit: Retrofit = getKoin().get()

        Log.e("OLD URL", retrofit.baseUrl().toString())

        retrofit = retrofit.newBuilder()
            .baseUrl(url)
            .client(getKoin().get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Log.e("NEW URL", retrofit.baseUrl().toString())
    }

    fun get(nameKey: String, defaultValue: String = ""): String {
        val preferences: PreferencesProvider = getKoin().get()
        return preferences.get(nameKey, defaultValue)
    }

    fun put(keyName: String, keyValue: String) {
        val preferences: PreferencesProvider = getKoin().get()
        preferences.put(keyName, keyValue)
    }
}

