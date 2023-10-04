package com.supertrain.navigator.presentation.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment()  {

    abstract val viewModel : BaseViewModel

}