package com.app.batman.utilities

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData


object LogicUtilityFunctions {
    fun<T> MutableLiveData<T>.checkMutableLiveData(input: T): Boolean {
        val update: T = input
        this.value = update
        return false
    }

    fun onBackPressed(activity: FragmentActivity, function: () -> Any) {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    function()
                }
            }
        activity.onBackPressedDispatcher.addCallback(activity, callback)
    }


    fun Context.toast(string: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, string, length).show()
    }
}