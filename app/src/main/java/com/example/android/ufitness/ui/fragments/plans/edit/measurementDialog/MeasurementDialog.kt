package com.example.android.ufitness.ui.fragments.plans.edit.measurementDialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.android.ufitness.R
import com.example.android.ufitness.models.ExercisePlans
import com.example.android.ufitness.ui.fragments.plans.edit.ExerciseSittingAdapter
import kotlinx.android.synthetic.main.measurement_dialog.view.*

class MeasurementDialog(
    private val esa: ExerciseSittingAdapter,
    private val onClickYes: (esa: ExerciseSittingAdapter) -> Unit,
) : DialogFragment() {

    private val exercisePlans: ExercisePlans = esa.exercisePlan!!
    private var isTimeBased: Boolean = exercisePlans.isTimeBased
    private var repeatCount: Int = exercisePlans.repeatCount

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.measurement_dialog, container, false)

        if (isTimeBased) {
            view.radio_blue.isChecked = true
            view.tvSignature.text = "секунд"
        }
        else {
            view.radio_red.isChecked = true
            view.tvSignature.text = "раз"
        }

        view.tvSignature.text = "раз"
        view.numberPicker.minValue = 1
        view.numberPicker.maxValue = 1000
        view.numberPicker.value = repeatCount

        view.radioGroup.setOnCheckedChangeListener { _, i ->
            if (i == R.id.radio_red){
                view.tvSignature.text = "раз"
                isTimeBased = false
            }
            else if (i == R.id.radio_blue) {
                view.tvSignature.text = "секунд"
                isTimeBased = true
            }

        }

        view.numberPicker.setOnScrollListener { _, _ ->
            repeatCount = view.numberPicker.value
        }

        view.btnYes.setOnClickListener {
            dismiss()
        }
        view.btnNo.setOnClickListener {
            esa.exercisePlan = ExercisePlans(
                id = exercisePlans.id,
                exerciseId = exercisePlans.exerciseId,
                planId = exercisePlans.planId,
                isTimeBased = isTimeBased,
                repeatCount = repeatCount
            )
            onClickYes.invoke(esa)
            dismiss()
        }

        return view
    }

}