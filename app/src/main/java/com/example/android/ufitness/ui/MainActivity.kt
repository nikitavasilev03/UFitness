package com.example.android.ufitness.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android.ufitness.MyApplication
import com.example.android.ufitness.R
import com.example.android.ufitness.ui.fragments.InfoFragment
import com.example.android.ufitness.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(MainActivityViewModel::class.java)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val info = SharedPreferencesUtils.getString(this, SharedPreferencesUtils.INFO_KEY)
        if (info == null) {
            InfoFragment().show(supportFragmentManager, null)
        }

        val isFirstLaunch =
            SharedPreferencesUtils.getBoolean(this, SharedPreferencesUtils.FIRST_LAUNCH_KEY)
        if (isFirstLaunch){
            SharedPreferencesUtils.saveBoolean(this, SharedPreferencesUtils.FIRST_LAUNCH_KEY, false)
            viewModel.generateInitExercises()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}