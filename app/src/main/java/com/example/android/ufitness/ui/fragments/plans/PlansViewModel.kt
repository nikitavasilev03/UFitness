package com.example.android.ufitness.ui.fragments.plans

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Plan
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlansViewModel @Inject constructor(val dataSource: DataSource): ViewModel() {
    private val dao = dataSource.database.planDao()

    val plansLiveData = MutableLiveData<List<Plan>>()

    init {
        viewModelScope.launch {
            plansLiveData.value = dao.getAll()
        }
    }

    fun deleteExercise(plan: Plan){
        viewModelScope.launch {
            dao.deletePlan(plan)
            plansLiveData.value = dao.getAll()
        }
    }

    fun fetchData() = viewModelScope.launch {
        plansLiveData.value = dao.getAll()
    }
}