package com.example.android.ufitness.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.models.Plan
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainFViewModel
    lateinit var plansAdapter: PlansAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(MainFViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plansAdapter = PlansAdapter(
                supportClick = ::supportClick,
                deleteClick = ::deleteClick,
                updateClick = ::updateClick
        )
    }

    private fun supportClick(plan: Plan) {

    }

    private fun deleteClick(plan: Plan) {

    }

    private fun updateClick(plan: Plan) {

    }


}