package com.example.android.ufitness.db.dao

import androidx.room.*
import com.example.android.ufitness.models.Plan

@Dao
interface PlanDao {

    @Insert
    fun insert(exercises: List<Plan>)

    @Query("SELECT * FROM plans")
    fun getAll(): List<Plan>

    @Query("DELETE FROM plans")
    fun deleteAll()

    @Delete
    fun deletePlan(plan: Plan)

    @Update
    fun updatePlan(plan: Plan)
}