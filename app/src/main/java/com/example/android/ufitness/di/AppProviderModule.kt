package com.example.android.ufitness.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.android.ufitness.db.AppDatabase
import com.example.android.ufitness.utils.DataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppProviderModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideDataSource(database: AppDatabase): DataSource =
            DataSource(database)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun providesApplication(): Application = application
}