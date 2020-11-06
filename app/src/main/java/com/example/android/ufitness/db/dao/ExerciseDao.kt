package com.example.android.ufitness.db.dao

import androidx.room.*
import com.example.android.ufitness.models.Exercise

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insert(exercises: List<Exercise>)

    @Query("SELECT * FROM exercises")
    suspend fun getAll(): List<Exercise>

    @Query("DELETE FROM exercises")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)
}