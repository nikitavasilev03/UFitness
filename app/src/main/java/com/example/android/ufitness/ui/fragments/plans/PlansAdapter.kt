package com.example.android.ufitness.ui.fragments.plans

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.android.ufitness.R
import com.example.android.ufitness.core.RecyclerAdapter
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.models.Plan
import kotlinx.android.synthetic.main.item_plan.view.*

class PlansAdapter(
        //private val supportClick: (model: Plan) -> Unit
    private val onDelete: (model: Plan) -> Unit,
    private val onUpdate: (model: Plan) -> Unit
) : RecyclerAdapter<Plan>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<Plan> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plan, parent, false)

        return object : BindingHolder<Plan>(view) {
            override fun bind(model: Plan, position: Int) {
                view.tvTitle.text = model.name
                view.tvPlanDesc.text = model.purpose
                view.ivUpdate.setOnClickListener {
                    onUpdate.invoke(model)
                }
                view.ivDelete.setOnClickListener {
                    onDelete.invoke(model)
                }

//                view.ivSupport.setOnClickListener {
//                    supportClick.invoke(model)
//                }
            }
        }
    }
}