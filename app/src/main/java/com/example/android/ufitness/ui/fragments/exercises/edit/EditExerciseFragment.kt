package com.example.android.ufitness.ui.fragments.exercises.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.models.Exercise
import kotlinx.android.synthetic.main.fragment_edit_exercise.*
import javax.inject.Inject

class EditExerciseFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: EditExerciseViewModel

    private var exercise: Exercise? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercise = arguments?.getParcelable(EXERCISE_KEY)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(EditExerciseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exercise?.let {
            etExerciseName.setText(it.name)
            etDesc.setText(it.description)
        }
        btnSave.setOnClickListener {
            val name = etExerciseName.text.toString()
            val desc = etDesc.text.toString()
            if (!name.isBlank() || !desc.isBlank()) {
                viewModel.manageExercise(exercise, name, desc)
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

    private fun observeLiveData() {
        viewModel.editCompleteLiveData.observe(viewLifecycleOwner) {
            if (it)
                findNavController().navigateUp()
        }
    }

    companion object {
        const val EXERCISE_KEY = "EXERCISE_KEY"
    }
}