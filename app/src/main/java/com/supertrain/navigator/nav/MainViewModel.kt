package com.supertrain.navigator.nav

import androidx.lifecycle.ViewModel
import com.supertrain.navigator.UiActions

class ActivityScopeViewModel(
    val uiActions: UiActions,
    val navigator: MediateNavigator
) : ViewModel(), Navigator by navigator, UiActions by uiActions {

    override fun onCleared() {
        super.onCleared()
        navigator.clear() // clear all activity actions
    }
}