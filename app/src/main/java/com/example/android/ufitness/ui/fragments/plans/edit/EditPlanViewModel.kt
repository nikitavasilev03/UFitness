package com.example.android.ufitness.ui.fragments.plans.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.models.Plan
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditPlanViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    private val dao = dataSource.database.planDao()
    private val daoExercises = dataSource.database.exerciseDao()

    val exercisesLiveData = MutableLiveData<List<Exercise>>()
    val editCompleteLiveData = MutableLiveData(false)

    val selectedExercises: MutableList<Int> = mutableListOf()

    init {
        viewModelScope.launch {
            exercisesLiveData.value = daoExercises.getAll()
        }
    }

    private fun insertNewPlan(plan: Plan) = viewModelScope.launch {
        dao.insert(listOf(plan))
    }

    private fun updatePlan(plan: Plan) = viewModelScope.launch {
        dao.updatePlan(plan)
    }

    fun managePlan(plan: Plan?, name: String, purpose: String) {
        if (plan == null) {
            insertNewPlan(Plan(name = name, purpose = purpose))
            editCompleteLiveData.value = true
        } else {
            updatePlan(plan.copy(name = name, purpose = purpose))
            editCompleteLiveData.value = true
        }
    }

    fun fetchData() = viewModelScope.launch {
        exercisesLiveData.value = daoExercises.getAll()
    }

    fun manageExercise(exercise: Exercise, isChecked: Boolean){
        if (exercise.id == null)
            return
        val id: Int = exercise.id
        if (isChecked)
            selectedExercises.add(id)
        else
            selectedExercises.remove(id)
    }

}