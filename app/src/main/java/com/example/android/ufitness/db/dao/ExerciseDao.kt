package com.example.android.ufitness.db.dao

import androidx.room.*
import com.example.android.ufitness.models.Exercise

@Dao
interface ExerciseDao {

    @Insert
    fun insert(exercises: List<Exercise>)

    @Query("SELECT * FROM exercises")
    fun getAll(): List<Exercise>

    @Query("DELETE FROM exercises")
    fun deleteAll()

    @Delete
    fun deleteExercise(exercise: Exercise)

    @Update
    fun updateExrecise(exercise: Exercise)
}