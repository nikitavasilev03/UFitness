package com.example.android.ufitness.ui.fragments.plans.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.models.ExercisePlans
import com.example.android.ufitness.models.Plan
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditPlanViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    private val planDao = dataSource.database.planDao()
    private val exerciseDao = dataSource.database.exerciseDao()
    private val exercisePlansDao = dataSource.database.exercisePlansDao()

    val exercisesLiveData = MutableLiveData<List<Exercise>>()
    val editCompleteLiveData = MutableLiveData(false)

    private val selectedExercises: MutableList<Int> = mutableListOf()
    private var planId: Int? = null

    init {
        viewModelScope.launch {
            exercisesLiveData.value = exerciseDao.getAll()
        }
    }

    private suspend fun insertNewPlan(plan: Plan) {
        planDao.insert(listOf(plan))
    }

    private suspend fun updatePlan(plan: Plan) {
        planDao.updatePlan(plan)
    }

    fun managePlan(plan: Plan?, name: String, purpose: String) = viewModelScope.launch {
        if (plan == null) {
            insertNewPlan(Plan(name = name, purpose = purpose))
        } else {
            updatePlan(plan.copy(name = name, purpose = purpose))
            exercisePlansDao.deleteAllByPlan(plan.id!!)
        }

        planId = planDao.getPlanIdByName(name)[0]
        planId?.let {
            val exercisePlans = mutableListOf<ExercisePlans>()
            selectedExercises.forEach { item ->
                exercisePlans.add(
                    ExercisePlans(
                        planId = planId!!,
                        exerciseId = item,
                        isTimeBased = false,
                        repeatCount = 0
                    )
                )
            }
            exercisePlansDao.insert(exercisePlans)
        }

        editCompleteLiveData.value = true
    }

    fun fetchData() = viewModelScope.launch {
        exercisesLiveData.value = exerciseDao.getAll()
    }

    fun manageExercise(exercise: Exercise, isChecked: Boolean) {
        if (exercise.id == null)
            return
        val id: Int = exercise.id
        if (isChecked)
            selectedExercises.add(id)
        else
            selectedExercises.remove(id)
    }
}