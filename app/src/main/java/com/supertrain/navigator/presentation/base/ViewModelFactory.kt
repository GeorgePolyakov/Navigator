package com.supertrain.navigator.presentation.base

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supertrain.navigator.nav.ARG_SCREEN
import com.supertrain.navigator.nav.MainNavigator
import com.supertrain.navigator.nav.Navigator
import com.supertrain.navigator.presentation.MainActivity

class ViewModelFactory(
    private val screen: BaseScreen,
    private val fragment: BaseFragment

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity() as MainActivity
        val application = hostActivity.application
        val navProvider =
            ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory(application))
        val navigator = navProvider[MainNavigator::class.java]
        val constructor = modelClass.getConstructor(Navigator::class.java, screen::class.java)
        return constructor.newInstance(navigator, screen)
    }
}

inline fun <reified VM : ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {
    val screen = arguments?.getSerializable(ARG_SCREEN) as BaseScreen
    ViewModelFactory(screen, this)
}