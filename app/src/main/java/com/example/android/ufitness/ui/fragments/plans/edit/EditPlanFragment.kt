package com.example.android.ufitness.ui.fragments.plans.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.models.Plan
import kotlinx.android.synthetic.main.fragment_edit_plan.*
import javax.inject.Inject

class EditPlanFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: EditPlanViewModel
    lateinit var exercisesAdapter: EditPlanExercisesAdapter

    private var plan: Plan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plan = arguments?.getParcelable(PLAN_KEY)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(EditPlanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plan?.let {
            etPlanName.setText(it.name)
            etPlanPurpose.setText(it.purpose)
        }

        exercisesAdapter = EditPlanExercisesAdapter(
            onChangeCheck = viewModel::manageExercise
        )
        exercisesCheckRecycler.apply {
            adapter = exercisesAdapter
        }

        btnPlanSave.setOnClickListener {
            val name = etPlanName.text.toString()
            val purpose = etPlanPurpose.text.toString()
            if (!name.isBlank() || !purpose.isBlank()) {
                viewModel.managePlan(plan, name, purpose)
            } else {
                Toast.makeText(
                    view.context,
                    "Необходимо заполнить все текстовые поля",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        observeLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }


    private fun observeLiveData() {
        viewModel.editCompleteLiveData.observe(viewLifecycleOwner) {
            if (it)
                findNavController().navigateUp()
        }
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                exercisesCheckRecycler.visibility = View.VISIBLE
                exercisesAdapter.submit(it)
                //tvHint.visibility = View.GONE
            } else {
                exercisesCheckRecycler.visibility = View.GONE
                //tvHint.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val PLAN_KEY = "PLAN_KEY"
    }
}