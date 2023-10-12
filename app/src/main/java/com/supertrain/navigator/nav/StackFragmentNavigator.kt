package com.supertrain.navigator.nav

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.supertrain.navigator.R
import com.supertrain.navigator.presentation.Event
import com.supertrain.navigator.presentation.base.BaseScreen

class StackFragmentNavigator(
    private val activity: AppCompatActivity,
    @IdRes private val containerId: Int,
    private val initialScreenCreator: () -> BaseScreen
) : Navigator{

    private var result:Event<Any>? = null

    override fun launch(screen: BaseScreen) {

    }

    override fun goBack(result: Any?) {
        if(result!=null){
            this.result = Event(result)
        }
        activity.onBackPressed()
    }

    fun onCreate(savedInstanceState: Bundle?) {

    }

    fun onDestroy() {

    }

    fun launchFragment(
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