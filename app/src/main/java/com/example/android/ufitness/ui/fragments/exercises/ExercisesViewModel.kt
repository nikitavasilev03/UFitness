package com.example.android.ufitness.ui.fragments.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject


class ExercisesViewModel @Inject constructor(val dataSource: DataSource): ViewModel() {
    private val dao = dataSource.database.exerciseDao()

    val exercisesLiveData = MutableLiveData<List<Exercise>>()

    init {
        viewModelScope.launch {
            exercisesLiveData.value = dao.getAll()
        }
    }

    fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            dao.deleteExercise(exercise)
        }
    }
}