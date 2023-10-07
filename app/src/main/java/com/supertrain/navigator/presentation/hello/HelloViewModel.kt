package com.supertrain.navigator.presentation.hello

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supertrain.navigator.R
import com.supertrain.navigator.presentation.edit.EditFragment
import com.supertrain.navigator.nav.Navigator
import com.supertrain.navigator.presentation.base.BaseViewModel

class HelloViewModel(
    private val navigator: Navigator,
    screen: HelloFragment.Screen
) : BaseViewModel() {

    private val _currentMessageLiveData = MutableLiveData<String>()
    val currentMessageLiveData: LiveData<String> = _currentMessageLiveData

    init {
        _currentMessageLiveData.value = navigator.getString(R.string.hello_world)
    }
    
    override fun onResult(result: Any) {
        if (result is String) {
            _currentMessageLiveData.value = result
        }
    }

    fun onEditPressed() { // press edit button
        navigator.launch(
            EditFragment.Screen(
                initialValue = currentMessageLiveData.value!!
            )
        )
    }
}