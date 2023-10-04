package com.supertrain.navigator.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.supertrain.navigator.R
import com.supertrain.navigator.databinding.ActivityMainBinding
import com.supertrain.navigator.presentation.hello.HelloFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.fragmentContainer, HelloFragment())
                .commit()
        }
    }
}