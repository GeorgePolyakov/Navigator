package com.supertrain.navigator.presentation.base

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias ViewModelCreator = ()  -> ViewModel?

class PlantViewModel(
    private val creator: ViewModelCreator
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return creator() as T
    }
}

inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(
    noinline creator: ViewModelCreator
) : Lazy<VM>{
    return viewModels { PlantViewModel(creator) }
}