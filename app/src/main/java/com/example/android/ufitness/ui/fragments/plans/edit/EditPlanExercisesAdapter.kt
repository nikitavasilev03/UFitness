package com.example.android.ufitness.ui.fragments.plans.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.android.ufitness.R
import com.example.android.ufitness.core.RecyclerAdapter
import com.example.android.ufitness.models.Exercise
import kotlinx.android.synthetic.main.check_item_exercise.view.*
import kotlinx.android.synthetic.main.item_exercise.view.tvExerciseDesc

class EditPlanExercisesAdapter(
    private val onChangeCheck: (model: Exercise, checked: Boolean) -> Unit
) : RecyclerAdapter<Exercise>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.BindingHolder<Exercise> {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.check_item_exercise, parent, false)
        return object : RecyclerAdapter.BindingHolder<Exercise>(view) {
            override fun bind(model: Exercise, position: Int) {
                view.tvExerciseTitle.text = model.name
                view.tvExerciseDesc.text = model.description
                view.cbCheckSelect.setOnCheckedChangeListener { _, isChecked -> onChangeCheck(model, isChecked) }
            }
        }
    }

}