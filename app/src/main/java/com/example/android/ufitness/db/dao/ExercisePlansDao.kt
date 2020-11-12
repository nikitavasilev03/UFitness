package com.example.android.ufitness.db.dao

import androidx.room.*
import com.example.android.ufitness.models.ExercisePlans

@Dao
interface ExercisePlansDao {

    @Insert
    suspend fun insert(items: List<ExercisePlans>)

    @Query("DELETE FROM exercisePlans")
    suspend fun deleteAll()

    @Update
    suspend fun updateItem(item: ExercisePlans)

    @Delete
    suspend fun deleteItem(item: ExercisePlans)
}