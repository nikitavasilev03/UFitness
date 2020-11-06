package com.example.android.ufitness.ui.fragments.plans.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Plan
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditPlanViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    private val dao = dataSource.database.planDao()

    val editCompleteLiveData = MutableLiveData(false)

    private fun insertNewExercise(plan: Plan) = viewModelScope.launch {
        dao.insert(listOf(plan))
    }

    private fun updateExercise(plan: Plan) = viewModelScope.launch {
        dao.updatePlan(plan)
    }

    fun manageExercise(plan: Plan?, name: String, purpose: String) {
        if (plan == null) {
            insertNewExercise(Plan(name = name, purpose = purpose))
            editCompleteLiveData.value = true
        } else {
            updateExercise(plan.copy(name = name, purpose = purpose))
            editCompleteLiveData.value = true
        }
    }
}