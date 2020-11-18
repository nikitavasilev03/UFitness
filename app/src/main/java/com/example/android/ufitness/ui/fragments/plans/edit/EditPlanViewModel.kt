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

    val exercisesLiveData = MutableLiveData<List<ExerciseSittingAdapter>>()
    val editCompleteLiveData = MutableLiveData(false)

    private val selectedExercises: MutableList<Int> = mutableListOf()
    private var planId: Int? = null

    init {
        viewModelScope.launch {

        }
    }

    fun setupPlan(plan: Plan?) = viewModelScope.launch{

        val exercises = exerciseDao.getAll()
        val esa = mutableListOf<ExerciseSittingAdapter>()
        exercises.forEach{ item ->
            esa.add(
                ExerciseSittingAdapter(
                    exercise = item,
                    exercisePlan = null
                )
            )
        }

        plan?.let {
            esa.forEach { item ->
                val exercisesPlan = exercisePlansDao.getItemsForPlanAndExerciseId(plan.id!!, item.exercise!!.id!!)
                if (exercisesPlan.count() > 0)
                    item.exercisePlan = exercisesPlan[0]
            }
        }

        exercisesLiveData.value = esa
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
            planId = planDao.getPlanIdByName(name)[0]
        } else {
            planId = plan.id!!
            updatePlan(plan.copy(name = name, purpose = purpose))
        }

        val exercisePlans = mutableListOf<ExercisePlans>()
        exercisesLiveData.value?.forEach { item ->
            item.exercisePlan?.let {
                exercisePlans.add(
                    ExercisePlans(
                        planId = planId!!,
                        exerciseId = it.exerciseId,
                        isTimeBased = it.isTimeBased,
                        repeatCount = it.repeatCount
                    )
                )
            }
        }
        exercisePlansDao.deleteAllByPlan(planId!!)
        exercisePlansDao.insert(exercisePlans)

        editCompleteLiveData.value = true
    }

    fun manageExercise(esa: ExerciseSittingAdapter, isChecked: Boolean) {
        esa.exercise?.let {
            if (esa.exercisePlan == null){
                esa.exercisePlan = ExercisePlans(
                    planId = 0,
                    exerciseId = it.id!!,
                    isTimeBased = false,
                    repeatCount = 15
                )
            } else {
                esa.exercisePlan = null
            }
        }
    }

    fun getPlanned(): List<ExerciseSittingAdapter>?{
        return exercisesLiveData.value?.filter { it.exercisePlan!=null}
    }

    fun buildString(item: ExerciseSittingAdapter): String{
        val s = "${item.exercise?.name}."
        val cond = if (item.exercisePlan?.isTimeBased == true) "Секунд" else "Повторений"
        return "$s ${item.exercisePlan?.repeatCount} $cond"
    }
}