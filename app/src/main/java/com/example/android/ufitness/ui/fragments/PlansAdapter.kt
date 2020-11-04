package com.example.android.ufitness.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.android.ufitness.R
import com.example.android.ufitness.core.RecyclerAdapter
import com.example.android.ufitness.models.Plan
import kotlinx.android.synthetic.main.item_plan.view.*

class PlansAdapter(
        private val deleteClick: (model: Plan) -> Unit,
        private val updateClick: (model: Plan) -> Unit,
        private val supportClick: (model: Plan) -> Unit
) : RecyclerAdapter<Plan>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<Plan> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plan, parent, false)

        return object : BindingHolder<Plan>(view) {
            override fun bind(model: Plan, position: Int) {
                view.tvTitle.text = model.name
                view.tvPlanDesc.text = model.purpose
                view.ivSupport.setOnClickListener {
                    supportClick.invoke(model)
                }
                view.ivUpdate.setOnClickListener {
                    updateClick.invoke(model)
                }
                view.ivDelete.setOnClickListener {
                    deleteClick.invoke(model)
                }
            }
        }
    }
}