package com.llamasoft.amazingkoin.app.di

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [/* Your table classes go here */], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

}