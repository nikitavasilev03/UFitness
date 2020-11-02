package com.example.android.ufitness.di

import android.content.Context
import androidx.room.Room
import com.example.android.ufitness.db.AppDatabase
import com.example.android.ufitness.utils.DataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppProviderModule {

    @Provides
    @Singleton
    fun provideDataSource(database: AppDatabase): DataSource =
            DataSource(database)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
    }
}