package com.example.android.ufitness.ui.fragments.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject


class ExercisesViewModel @Inject constructor(val dataSource: DataSource): ViewModel() {
    private val exerciseDao = dataSource.database.exerciseDao()
    private val exercisePlansDao = dataSource.database.exercisePlansDao()

    val exercisesLiveData = MutableLiveData<List<Exercise>>()

    init {
        viewModelScope.launch {
            exercisesLiveData.value = exerciseDao.getAll()
        }
    }

    fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            exercisePlansDao.deleteAllByExercise(exercise.id!!)
            exerciseDao.deleteExercise(exercise)
            exercisesLiveData.value = exerciseDao.getAll()
        }
    }

    fun fetchData() = viewModelScope.launch {
        exercisesLiveData.value = exerciseDao.getAll()
    }
}