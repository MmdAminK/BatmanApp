package com.app.batman.services.utilities

import android.content.Context
import com.app.batman.utilities.LogicUtilityFunctions.toast
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import retrofit2.Response

object CheckRequest {

    suspend fun<T> check(response: Response<T>, context: Context): T? =
        when(response.code()) {
            200 -> response.body()
            400 -> {
                //bad request
                withContext(Main) {
                    context.toast(ErrorHandler.badRequest)
                }
                null
            }
            408 -> {
                //time out
                withContext(Main) {
                    context.toast(ErrorHandler.timeOut)
                }
                null
            }
            else -> {
                withContext(Main) {
                    context.toast(ErrorHandler.badRequest)
                }
                null
            }
        }
}