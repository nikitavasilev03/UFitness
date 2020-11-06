package com.example.android.ufitness.ui.fragments.exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.models.Exercise
import com.example.android.ufitness.ui.fragments.exercises.edit.EditExerciseFragment
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject

class ExercisesFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: ExercisesViewModel
    lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(ExercisesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exercisesAdapter = ExercisesAdapter(
            onDelete = viewModel::deleteExercise,
            onUpdate = ::onUpdateClicked
        )
        exercisesRecycler.apply {
            adapter = exercisesAdapter
        }
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_exercisesFragment_to_editExerciseFragment)
        }
        observeLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    private fun observeLiveData() {
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                exercisesRecycler.visibility = View.VISIBLE
                exercisesAdapter.submit(it)
                tvHint.visibility = View.GONE
            } else {
                exercisesRecycler.visibility = View.GONE
                tvHint.visibility = View.VISIBLE
            }
        }
    }

    private fun onUpdateClicked(exercise: Exercise) {
        findNavController().navigate(
            R.id.action_exercisesFragment_to_editExerciseFragment,
            Bundle().apply { putParcelable(EditExerciseFragment.EXERCISE_KEY, exercise) })
    }
}