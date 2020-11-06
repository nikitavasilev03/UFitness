package com.example.android.ufitness.ui.fragments.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.android.ufitness.R
import com.example.android.ufitness.core.RecyclerAdapter
import com.example.android.ufitness.models.Exercise
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExercisesAdapter(
    private val onDelete: (model: Exercise) -> Unit,
    private val onUpdate: (model: Exercise) -> Unit
) : RecyclerAdapter<Exercise>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<Exercise> {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return object : BindingHolder<Exercise>(view) {
            override fun bind(model: Exercise, position: Int) {
                view.tvTitle.text = model.name
                view.tvExerciseDesc.text = model.description
                view.ivDelete.setOnClickListener {
                    onDelete.invoke(model)
                }
                view.ivUpdate.setOnClickListener {
                    onUpdate.invoke(model)
                }
            }
        }
    }

}