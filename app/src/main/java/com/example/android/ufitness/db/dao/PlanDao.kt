package com.example.android.ufitness.db.dao

import androidx.room.*
import com.example.android.ufitness.models.Plan

@Dao
interface PlanDao {

    @Insert
    suspend fun insert(plans: List<Plan>)

    @Query("SELECT * FROM plans")
    suspend fun getAll(): List<Plan>

    @Query("DELETE FROM plans")
    suspend fun deleteAll()

    @Delete
    suspend fun deletePlan(plan: Plan)

    @Update
    suspend fun updatePlan(plan: Plan)
}