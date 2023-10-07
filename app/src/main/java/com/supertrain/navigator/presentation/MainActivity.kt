package com.supertrain.navigator.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.supertrain.navigator.R
import com.supertrain.navigator.databinding.ActivityMainBinding
import com.supertrain.navigator.nav.MainNavigator
import com.supertrain.navigator.presentation.base.BaseFragment
import com.supertrain.navigator.presentation.base.BaseViewModel
import com.supertrain.navigator.presentation.hello.HelloFragment

class MainActivity : AppCompatActivity() {

    private val navigator by viewModels<MainNavigator> {
        ViewModelProvider.AndroidViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
           navigator.launchFragment(this, HelloFragment.Screen(), false)
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallback, false)
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.mainActivity = this
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.mainActivity = null
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallback)
    }

    private val fragmentCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            if(supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true) // showBackArrow on toolbar
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false) // hide back arrow  on toolbar
            }

            val result = navigator.resultLiveData.value?.getValue() ?: return
            if(f is BaseFragment){
                f.viewModel.onResult(result)
            }
        }
    }
}