package com.example.android.ufitness.db.dao

import androidx.room.*
import com.example.android.ufitness.models.ExercisePlans
import com.example.android.ufitness.models.Plan

@Dao
interface ExercisePlansDao {

    @Insert
    suspend fun insert(items: List<ExercisePlans>)

    @Query("DELETE FROM exercisePlans")
    suspend fun deleteAll()

    @Query("DELETE FROM exercisePlans WHERE planId = :pId")
    suspend fun deleteAllByPlan(pId: Int)

    @Update
    suspend fun updateItem(item: ExercisePlans)

    @Delete
    suspend fun deleteItem(item: ExercisePlans)

    @Query("SELECT * FROM exercisePlans WHERE planId = :planId")
    suspend fun getItemsForPlanId(planId: Int): List<ExercisePlans>

    @Query("SELECT * FROM exercisePlans WHERE planId = :planId and exerciseId = :exerciseId")
    suspend fun getItemsForPlanAndExerciseId(planId: Int, exerciseId: Int): List<ExercisePlans>
}