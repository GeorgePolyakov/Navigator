package com.supertrain.navigator.presentation.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supertrain.navigator.R
import com.supertrain.navigator.databinding.FragmentHelloBinding
import com.supertrain.navigator.presentation.edit.EditFragment
import com.supertrain.navigator.presentation.base.BaseScreen

class HelloFragment : Fragment() {

    class Screen : BaseScreen

    private var binding:FragmentHelloBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelloBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.editButton?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EditFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}