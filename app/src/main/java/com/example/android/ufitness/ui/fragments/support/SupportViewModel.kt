package com.example.android.ufitness.ui.fragments.support

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.models.ExercisePlans
import com.example.android.ufitness.models.Plan
import com.example.android.ufitness.utils.DataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class SupportViewModel @Inject constructor(val dataSource: DataSource) : ViewModel() {

    private var plan: Plan? = null

    lateinit var timer: CountDownTimer

    val expLiveData = MutableLiveData<List<ExercisePlans>>()
    val exerciseLiveData = MutableLiveData<Exercise>()
    val stateLiveData = MutableLiveData<State>()
    val currentTime = MutableLiveData<Long>()
    val timerEnd = MutableLiveData<Boolean?>(null)
    private val expDao = dataSource.database.exercisePlansDao()
    private val exerciseDao = dataSource.database.exerciseDao()

    init {
        expLiveData.observeForever {
            if (it.isNotEmpty()) {
                timerEnd.value = false
                if (it[0].isTimeBased) {
                    timer = object : CountDownTimer(it[0].repeatCount * ONE_SECOND, ONE_SECOND) {
                        override fun onTick(millisUntilFinished: Long) {
                            currentTime.value = (millisUntilFinished / ONE_SECOND)
                        }

                        override fun onFinish() {
                            currentTime.value = DONE
                            timerEnd.value = true
                        }
                    }
                }
                else timerEnd.value = null
            }
        }
    }

    fun setupPlan(p: Plan) = viewModelScope.launch {
        plan = p
        plan!!.id?.let {
            expLiveData.value = expDao.getItemsForPlanId(it)
        }
    }

    fun loadNextExercise() = viewModelScope.launch {
        val exp = expLiveData.value
        exp?.let {
            if (it.isNotEmpty())
                exerciseLiveData.value = exerciseDao.getExerciseById(exp[0].exerciseId)[0]
        }
    }

    fun popCurrent() {
        val list = expLiveData.value
        list?.let {
            val new = it as MutableList
            new.removeAt(0)
            expLiveData.value = new
            if (new.isEmpty()) stateLiveData.value = State.FINISH
        }
    }

    enum class State {
        FINISH
    }

    companion object {
        private const val ONE_SECOND = 1000L
        private const val DONE = 0L
    }
}