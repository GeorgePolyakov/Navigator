package com.supertrain.navigator.nav

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supertrain.navigator.R
import com.supertrain.navigator.presentation.Event
import com.supertrain.navigator.presentation.MainActivity
import com.supertrain.navigator.presentation.base.BaseScreen

class MainNavigator(
    private val application: Application
) : AndroidViewModel(application), Navigator {

    private val argument = "ARGS"
    val whenActivityActive = MainActivityActions() // lambda type (MainActivity) -> Unit

    private val _result = MutableLiveData<Event<Any>>()
    val resultLiveData: LiveData<Event<Any>> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive {
        launchFragment(it, screen, true)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    /* we cant refer on Main Activity case after changing screen orientation Activity will have
     other link*/

    override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int): String {
        return getApplication<Application>().getString(messageRes)
    }

    private fun launchFragment(
        activity: MainActivity,
        screen: BaseScreen,
        addToBackStack: Boolean = true
    ) {
        val fragment =
            screen.javaClass.enclosingClass.getDeclaredConstructor() // javaClass extension function returning : Class<T>
                .newInstance() as Fragment // newInstance() Reflection mechanism
        fragment.arguments = bundleOf(argument to screen)// screen is serializable
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.fragmentContainer, fragment)
    }
}