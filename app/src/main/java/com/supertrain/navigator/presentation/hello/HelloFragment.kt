package com.supertrain.navigator.presentation.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supertrain.navigator.R
import com.supertrain.navigator.databinding.FragmentHelloBinding
import com.supertrain.navigator.presentation.base.BaseFragment
import com.supertrain.navigator.presentation.edit.EditFragment
import com.supertrain.navigator.presentation.base.BaseScreen
import com.supertrain.navigator.presentation.base.screenViewModel

class HelloFragment : BaseFragment() {

    class Screen : BaseScreen

    override val viewModel by screenViewModel<HelloViewModel>()

    private var binding:FragmentHelloBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelloBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.editButton?.setOnClickListener { viewModel.onEditPressed() }

        viewModel.currentMessageLiveData.observe(viewLifecycleOwner) {
            binding?.helloTextView?.text = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}