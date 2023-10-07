package com.supertrain.navigator.nav

import androidx.annotation.StringRes
import com.supertrain.navigator.presentation.base.BaseScreen

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result:Any? = null)
    
    fun toast(@StringRes messageRes: Int)

    fun getString(@StringRes messageRes: Int) : String
}