package com.example.android.ufitness.ui.fragments.plans.edit

import android.content.Intent
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
import com.example.android.ufitness.ui.fragments.plans.edit.measurementDialog.MeasurementDialog
import com.example.android.ufitness.utils.SharedPreferencesUtils
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
        viewModel.setupPlan(plan)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
            onChangeCheck = ::onCheckExercise,
            onUpdate = ::onUpdateExerciseClicked
        )
        exercisesCheckRecycler.apply {
            adapter = exercisesAdapter
        }

        btnPlanSave.setOnClickListener {
            if (viewModel.countExercisePlans == 0) {
                Toast.makeText(
                    view.context,
                    "Необходимо выбрать хотя бы одно упражнение",
                    Toast.LENGTH_LONG
                ).show()
            } else {
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
        }

        btnShare.setOnClickListener {
            val name = etPlanName.text.toString()
            if (name.isBlank()) {
                Toast.makeText(
                    view.context,
                    "Необходимо заполнить все текстовые поля",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val info =
                    SharedPreferencesUtils.getString(view.context, SharedPreferencesUtils.INFO_KEY)
                val planned = viewModel.getPlanned()
                var t = "$info составил(а) тренировку $name"
                planned?.let {
                    it.forEach { item-> t+="\n ${viewModel.buildString(item)}" }
                }
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, t)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
        observeLiveData()
    }

    private fun onCheckExercise(esa: ExerciseSittingAdapter, isChecked: Boolean) {
        viewModel.manageExercise(esa, isChecked)
        exercisesAdapter.notifyDataSetChanged()
    }

    private fun onUpdateExerciseClicked(esa: ExerciseSittingAdapter) {
        esa.exercisePlan?.let {
            val dialog = MeasurementDialog(
                esa,
                ::onUpdateExerciseComplete
            )
            dialog.show(parentFragmentManager, "MeasurementDialog")
        }
    }

    private fun onUpdateExerciseComplete(esa: ExerciseSittingAdapter) {
        exercisesAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
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
            } else {
                exercisesCheckRecycler.visibility = View.GONE
            }
        }
    }

    companion object {
        const val PLAN_KEY = "PLAN_KEY"
    }
}