package com.supertrain.navigator.nav

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.supertrain.navigator.R
import com.supertrain.navigator.presentation.Event
import com.supertrain.navigator.presentation.base.BaseFragment
import com.supertrain.navigator.presentation.base.BaseScreen
import com.supertrain.navigator.presentation.base.HasScreenTitle
import com.supertrain.navigator.presentation.edit.EditFragment
import com.supertrain.navigator.presentation.edit.EditViewModel
import com.supertrain.navigator.presentation.hello.HelloFragment

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
        if(savedInstanceState == null){
            launchFragment(
                screen = HelloFragment.Screen(),
                false
            )
        }
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallback, false)
    }

    fun onDestroy() {
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallback)
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


    private val fragmentCallback = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentAttached(fm, f, context)

            Log.d("fragmentCallback", " fragment ${f.javaClass.name} was attached")
        }

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            Log.d("fragmentCallback", "fragment backStackCount ${activity.supportFragmentManager.backStackEntryCount}")
            Log.d("fragmentCallback", "current fragment is  ${f.javaClass.name}")
            if(activity.supportFragmentManager.backStackEntryCount > 0) {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true) // showBackArrow on toolbar
            } else {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(false) // hide back arrow  on toolbar
            }

            if(f is HasScreenTitle){
                activity?.supportActionBar?.title = f.getScreenTitle()
            } else {
                activity?.supportActionBar?.title = "Simple Navigation"
            }



            val result = result?.getValue() ?: return
            if(f is BaseFragment){
                f.viewModel.onResult(result) // trigger liveData withResult
            }
        }

        override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
            super.onFragmentDetached(fm, f)
            Log.d("fragmentCallback", " fragment ${f.javaClass.name} was detached")

        }
    }
}