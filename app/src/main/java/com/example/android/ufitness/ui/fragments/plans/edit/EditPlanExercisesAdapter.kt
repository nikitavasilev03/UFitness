package com.example.android.ufitness.ui.fragments.plans.edit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.ufitness.R
import com.example.android.ufitness.core.RecyclerAdapter
import kotlinx.android.synthetic.main.check_item_exercise.view.*
import kotlinx.android.synthetic.main.check_item_exercise.view.ivUpdate

class EditPlanExercisesAdapter(
    private val onChangeCheck: (model: ExerciseSittingAdapter, checked: Boolean, pos: Int) -> Unit,
    private val onUpdate: (model: ExerciseSittingAdapter) -> Unit
) : RecyclerAdapter<ExerciseSittingAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.BindingHolder<ExerciseSittingAdapter> {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.check_item_exercise, parent, false)
        return object : RecyclerAdapter.BindingHolder<ExerciseSittingAdapter>(view) {
            override fun bind(model: ExerciseSittingAdapter, position: Int) {
                model.exercise?.let {
                    view.tvExerciseTitle.text = it.name
                }
                view.cbCheckSelect.setOnCheckedChangeListener(null)
                if (model.exercisePlan == null)
                {
                    view.cbCheckSelect.isChecked = false
                    view.tvMeasurement.text = ""
                    view.tvCount.text = ""
                    view.ivUpdate.visibility = View.GONE
                } else {
                    view.cbCheckSelect.isChecked = true
                    view.tvMeasurement.text = if (model.exercisePlan!!.isTimeBased) "Время" else "Повторения"
                    view.tvCount.text = model.exercisePlan!!.repeatCount.toString() + if (model.exercisePlan!!.isTimeBased) " секунд" else " раз"
                    view.ivUpdate.visibility = View.VISIBLE
                }
                view.cbCheckSelect.setOnCheckedChangeListener { _, isChecked -> onChangeCheck(model, isChecked, position) }
                view.ivUpdate.setOnClickListener {
                    onUpdate.invoke(model)
                }
            }
        }
    }
}