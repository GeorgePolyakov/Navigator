package com.supertrain.navigator.presentation.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supertrain.navigator.databinding.FragmentEditBinding
import com.supertrain.navigator.presentation.base.BaseFragment
import com.supertrain.navigator.presentation.base.BaseScreen
import com.supertrain.navigator.presentation.base.HasScreenTitle
import com.supertrain.navigator.presentation.base.screenViewModel

class EditFragment : BaseFragment(), HasScreenTitle {

    class Screen(
        val initialValue: String
    ) : BaseScreen

    override val viewModel by screenViewModel<EditViewModel>()

    private var binding: FragmentEditBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initialMessageEventLiveData.observe(viewLifecycleOwner) {
            it.getValue()?.let { message ->
                binding?.valueEditText?.setText(message)
            }
        }

        binding?.saveButton?.setOnClickListener {
            viewModel.onSavePressed(binding?.valueEditText?.text.toString())
        }

        binding?.cancelButton?.setOnClickListener {
            viewModel.onCancelPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun getScreenTitle(): String = "Edit Fragment"
}