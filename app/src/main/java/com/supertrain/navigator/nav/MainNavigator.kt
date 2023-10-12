package com.supertrain.navigator.nav

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supertrain.navigator.R
import com.supertrain.navigator.UiActions
import com.supertrain.navigator.presentation.Event
import com.supertrain.navigator.presentation.MainActivity
import com.supertrain.navigator.presentation.base.BaseScreen

const val ARG_SCREEN = "SCREEN"

class MainNavigator(
    private val uiActions: UiActions
) : ViewModel(), Navigator {

    var whenActivityActive = MainActivityActions() // lambda type (MainActivity) -> Unit

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

  /*  override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int): String {
        return getApplication<Application>().getString(messageRes)
    }*/

    fun launchFragment(
        activity: MainActivity,
        screen: BaseScreen,
        addToBackStack: Boolean = true
    ) {
        val constructorArray = screen.javaClass.enclosingClass.declaredConstructors
        for(constructor in constructorArray){
            Log.d("launchFragment", " contructor of screen class is : ${constructor}")

        }

        val fragment =
            screen.javaClass.enclosingClass.getDeclaredConstructor() // javaClass extension function returning : Class<T>
                .newInstance() as Fragment // newInstance() Reflection mechanism
        fragment.arguments = bundleOf(ARG_SCREEN to screen)// screen is serializable
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}