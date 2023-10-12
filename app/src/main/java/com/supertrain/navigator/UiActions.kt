package com.supertrain.navigator

import android.app.Application
import android.content.Context
import android.widget.Toast

interface UiActions {

    fun toast(messageRes: Int)

    fun getString(messageRes: Int) : String


    class AndroidUiActions( private val applicationContext : Context) : UiActions {

        override fun toast(messageRes: Int) {
            Toast.makeText(applicationContext, messageRes, Toast.LENGTH_SHORT).show()
        }

        override fun getString(messageRes: Int): String {
            return applicationContext.getString(messageRes)
        }
    }
}