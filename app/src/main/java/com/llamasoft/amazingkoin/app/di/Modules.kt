package com.llamasoft.amazingkoin.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {

}

val repositoryModule = module {

}

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "llama.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    factory { provideDatabase(androidApplication()) }
}

val applicationModule = module {
    single { PreferencesProvider(androidContext()) }
}

val networkModule = module {
    single { provideCache(androidApplication()) }
    single { provideOkHttpClient(get()) }
    factory { provideRetrofit(get()) }
}

class PreferencesProvider(context: Context) {
    var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun get(nameKey: String, defaultValue: String = ""): String {
        return preferences.getString(nameKey, defaultValue)?: ""
    }

    fun put(keyName: String, keyValue: String) {
        preferences.edit().putString(keyName, keyValue).apply()
    }
}

fun provideCache(application: Application): Cache {
    val cacheSize = 10 * 1024 * 1024
    return Cache(application.cacheDir, cacheSize.toLong())
}

fun provideOkHttpClient(
    cache: Cache
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .cache(cache)
        .build()
}

fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .baseUrl("http://10.28.21.39/GREATE/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()



