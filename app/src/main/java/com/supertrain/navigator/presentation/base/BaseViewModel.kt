package com.supertrain.navigator.presentation.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){
    open fun onResult(result: Any){}
}