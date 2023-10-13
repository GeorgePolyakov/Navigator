package com.supertrain.navigator.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.supertrain.navigator.R
import com.supertrain.navigator.databinding.ActivityMainBinding
import com.supertrain.navigator.nav.ActivityScopeViewModel
import com.supertrain.navigator.nav.MainNavigator
import com.supertrain.navigator.nav.StackFragmentNavigator
import com.supertrain.navigator.presentation.base.BaseFragment
import com.supertrain.navigator.presentation.base.ViewModelFactory
import com.supertrain.navigator.presentation.hello.HelloFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navigator : StackFragmentNavigator

    private val activityViewModel by viewModels<ActivityScopeViewModel> {
        ViewModelProvider.AndroidViewModelFactory(application)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator = StackFragmentNavigator(
            activity = this,
            R.id.fragmentContainer
        ) { HelloFragment.Screen() }

        navigator.onCreate(savedInstanceState) // we may not call callback methodes we can use lifecycle owner
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.navigator.setTargetNavigator(navigator) // init target navigator
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.mainActivity = null
    }

    override fun onDestroy() { // we may not call callback methodes we can use lifecycle owner
        navigator.onDestroy()
        super.onDestroy()
    }
}