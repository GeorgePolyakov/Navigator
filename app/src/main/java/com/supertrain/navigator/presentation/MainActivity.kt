package com.supertrain.navigator.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.supertrain.navigator.R
import com.supertrain.navigator.UiActions
import com.supertrain.navigator.databinding.ActivityMainBinding
import com.supertrain.navigator.nav.ActivityScopeViewModel
import com.supertrain.navigator.nav.MediateNavigator
import com.supertrain.navigator.nav.StackFragmentNavigator
import com.supertrain.navigator.presentation.base.viewModelCreator
import com.supertrain.navigator.presentation.hello.HelloFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navigator : StackFragmentNavigator

    private val activityViewModel by viewModelCreator <ActivityScopeViewModel> {
     ActivityScopeViewModel(
         uiActions = UiActions.AndroidUiActions(applicationContext),
         navigator  = MediateNavigator()
     )
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
        activityViewModel.navigator.setTargetNavigator(null) // clear target navigator when other viewModels will call commands theese comands will be in navigator list
    }

    override fun onDestroy() { // we may not call callback methodes we can use lifecycle owner
        navigator.onDestroy()
        super.onDestroy()
    }
}