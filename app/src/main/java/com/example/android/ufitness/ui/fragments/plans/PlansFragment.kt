package com.example.android.ufitness.ui.fragments.plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.models.Plan
import kotlinx.android.synthetic.main.fragment_plans.*
import javax.inject.Inject

class PlansFragment: Fragment() {
    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: PlansViewModel
    lateinit var planAdapter: PlansAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(PlansViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plans, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planAdapter = PlansAdapter (
            onDelete = viewModel::deleteExercise,
            onUpdate = ::onUpdateClicked
        )
        plansRecycler.apply {
            adapter = planAdapter
        }
        fabAddPlan.setOnClickListener {
            findNavController().navigate(R.id.action_plansFragment_to_editPlanFragment)
        }
        observeLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    private fun observeLiveData() {
        viewModel.plansLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                plansRecycler.visibility = View.VISIBLE
                planAdapter.submit(it)
                tvHintPlans.visibility = View.GONE
            } else {
                plansRecycler.visibility = View.GONE
                tvHintPlans.visibility = View.VISIBLE
            }
        }
    }

    private fun onUpdateClicked(plan: Plan) {
//        findNavController().navigate(
//            R.id.action_plansFragment_to_editPlanFragment,
//            Bundle().apply { putParcelable(EditExerciseFragment.EXERCISE_KEY, plan) })
    }
}