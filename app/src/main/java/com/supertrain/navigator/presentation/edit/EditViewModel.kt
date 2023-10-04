package com.supertrain.navigator.presentation.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supertrain.navigator.R
import com.supertrain.navigator.presentation.Event
import com.supertrain.navigator.presentation.Navigator
import com.supertrain.navigator.presentation.base.BaseViewModel

class EditViewModel(
    private val navigator: Navigator,
    private val screen: EditFragment.Screen
) : BaseViewModel() {

    private val _initialMessageEvent = MutableLiveData<Event<String>>()
    val initialMessageEventLiveData: LiveData<Event<String>> = _initialMessageEvent

    init {
        _initialMessageEvent.value = Event(screen.initialValue)
    }

    fun onSavePressed(message: String) {
        if(message.isBlank()){
            navigator.toast(R.string.message_must_not_be_empty)
            return
        }
        navigator.goBack(message)
    }

    fun onCancelPressed() {
        navigator.goBack()
    }
}