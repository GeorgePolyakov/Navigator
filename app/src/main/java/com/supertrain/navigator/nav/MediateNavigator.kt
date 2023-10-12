package com.supertrain.navigator.nav

import com.supertrain.navigator.presentation.base.BaseScreen

// No dependencies from activity
class MediateNavigator : Navigator  {

    private val targetNavigator = ResourceActions<Navigator>()

    override fun launch(screen: BaseScreen) = targetNavigator {
            it.launch(screen)
        }

    override fun goBack(result: Any?)  = targetNavigator {
        it.goBack(result)
    }

    fun setTargetNavigator (navigator: Navigator?){ // when Activity is destroying
        targetNavigator.resource = navigator
    }

    fun clear(){
        targetNavigator.clear()
    }
}