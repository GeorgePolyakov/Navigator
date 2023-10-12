package com.supertrain.navigator.nav

import com.supertrain.navigator.presentation.MainActivity


typealias ResourceAction<T> = (T) -> Unit

class ResourceActions <T> {

    private val actions = mutableListOf<ResourceAction<T>>()

    var resource: T? = null
        set(newValue){
            field = newValue
            if(newValue!=null){
                actions.forEach { // if resource(MainActivity) is available we should execute delayed actions
                    it(newValue)  // we cant run screen if activity is collapsed
                }
                actions.clear()
            }
        }

    operator fun invoke(resourceAction: ResourceAction<T>){
        val resource = this.resource
        if(resource == null){
            actions += resourceAction
        } else {
            resourceAction(resource) // execute resourceAction if resource is active
        }
    }

    fun clear() {
        actions.clear()
    }
}