package com.example.android.ufitness.ui.fragments.support

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.models.Plan
import com.example.android.ufitness.ui.fragments.support.SupportViewModel
import kotlinx.android.synthetic.main.fragment_support.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SupportFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: SupportViewModel

    private var plan: Plan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(SupportViewModel::class.java)
        plan = arguments?.getParcelable(PLAN_KEY) ?: throw IllegalArgumentException()
        plan?.let { viewModel.setupPlan(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        observeLiveData()
    }

    private fun initClicks() {
        btnLaunch.setOnClickListener {
            viewModel.timer.start()
            btnLaunch.visibility = View.GONE
        }

        btnNext.setOnClickListener {
            val list = viewModel.expLiveData.value
            list?.let {
                if (it.isNotEmpty()) {
                    viewModel.popCurrent()
                } else {
                    share()
                }
            }
        }
    }

    private fun share() {

    }

    private fun observeLiveData() {
        viewModel.expLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                viewModel.loadNextExercise()
            } else if (it.isEmpty()) {
                btnNext.text = "Поделиться"
            }
        }
        viewModel.exerciseLiveData.observe(viewLifecycleOwner) {
            tvExerciseName.text = getString(R.string.exercise_template, it.name)
            tvExerciseDesc.text = it.description
            viewModel.expLiveData.value?.let {
                tvLeft.text =
                    if (it[0].isTimeBased) "Время на упражнение" else "Количество повторений"
                tvCount.text = it[0].repeatCount.toString()
                if (!it[0].isTimeBased)
                    btnLaunch.visibility = View.GONE
                else
                    btnLaunch.visibility = View.VISIBLE
            }
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                SupportViewModel.State.FINISH -> {
                    tvExerciseDesc.visibility = View.INVISIBLE
                    tvDesc.visibility = View.INVISIBLE
                    tvExerciseName.visibility = View.INVISIBLE
                    tvLeft.visibility = View.INVISIBLE
                    btnLaunch.visibility = View.GONE
                    tvCount.text = "Тренировка завершена"
                }
                else -> { }
            }
        }
        viewModel.currentTime.observe(viewLifecycleOwner) {
            tvCount.text = DateUtils.formatElapsedTime(it)
        }
        viewModel.timerEnd.observe(viewLifecycleOwner) {
            it?.let {
                if (it)
                    btnNext.visibility = View.VISIBLE
                else btnNext.visibility = View.INVISIBLE
                return@observe
            }
            btnNext.visibility = View.VISIBLE
        }
    }

    companion object {
        const val PLAN_KEY = "PLAN_KEY"
    }
}