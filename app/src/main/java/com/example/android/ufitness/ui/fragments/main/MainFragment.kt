package com.example.android.ufitness.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.models.Plan
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainFViewModel
    lateinit var plansAdapter: MainPlansAdapter

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
        plansAdapter = MainPlansAdapter(
            supportClick = ::supportClick,
        )
        plansRecycler.apply {
            adapter = plansAdapter
        }
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPlans()
    }

    private fun initObservers(){
        viewModel.plansLiveData.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()){
                plansRecycler.visibility = View.VISIBLE
                plansAdapter.submit(it)
                tvHint.visibility = View.GONE
            } else {
                plansRecycler.visibility = View.GONE
                tvHint.visibility = View.VISIBLE
            }
        }
    }

    private fun supportClick(plan: Plan) {

    }
}