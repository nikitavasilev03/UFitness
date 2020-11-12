package com.example.android.ufitness.ui.fragments.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Plan
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFViewModel @Inject constructor(val dataSource: DataSource): ViewModel() {

    private val dao = dataSource.database.planDao()

    var plans: List<Plan>? = null
    set(value) {
        plansLiveData.value = value
        field = value
    }

    val plansLiveData = MutableLiveData<List<Plan>>()

    init {
        viewModelScope.launch {
            plans = dao.getAll()
        }
    }

    fun loadPlans() = viewModelScope.launch {
        plans = dao.getAll()
    }
}