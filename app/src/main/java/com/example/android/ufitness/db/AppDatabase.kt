package com.example.android.ufitness.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.ufitness.db.AppDatabase.Companion.DB_VERSION
import com.example.android.ufitness.db.dao.ExerciseDao
import com.example.android.ufitness.db.dao.PlanDao
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.models.Plan

@Database(
    entities =[
        Exercise::class,
        Plan::class
    ], version = DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_VERSION = 1
    }

    abstract fun exerciseDao(): ExerciseDao
    abstract fun planDao(): PlanDao
}