package com.example.android.ufitness.ui.fragments.exercises.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditExerciseViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    private val dao = dataSource.database.exerciseDao()

    val editCompleteLiveData = MutableLiveData(false)

    private fun insertNewExercise(exercise: Exercise) = viewModelScope.launch {
        dao.insert(listOf(exercise))
    }


    private fun updateExercise(exercise: Exercise) = viewModelScope.launch {
        dao.updateExercise(exercise)
    }

    fun manageExercise(exercise: Exercise?, name: String, desc: String) {
        if (exercise == null) {
            insertNewExercise(Exercise(name = name, description = desc))
            editCompleteLiveData.value = true
        } else {
            updateExercise(exercise.copy(name = name, description = desc))
            editCompleteLiveData.value = true
        }
    }
}