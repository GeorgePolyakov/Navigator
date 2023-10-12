package com.supertrain.navigator.nav

import com.supertrain.navigator.presentation.MainActivity

typealias MainActivityAction = (MainActivity) -> Unit


class MainActivityActions {

    private val actions = mutableListOf<MainActivityAction>()

    var mainActivity:MainActivity? = null
        set(activity){
            field = activity
            if(activity!=null){
                actions.forEach { // if resource(MainActivity) is available we should execute delayed actions
                    it(activity)
                }
                actions.clear()
            }
        }

    operator fun invoke(activityAction: (MainActivity) -> Unit){
        val activity = this.mainActivity
        if(activity == null){
            actions += activityAction
        } else {
            activityAction(activity)
        }
    }

    fun clear() {
        actions.clear()
    }
}