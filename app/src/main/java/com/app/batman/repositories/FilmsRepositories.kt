package com.app.batman.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.app.batman.database.BatmanDb
import com.app.batman.models.Film
import com.app.batman.models.MainPageDto
import com.app.batman.services.retrofit_builder.ApiServices
import com.app.batman.services.retrofit_builder.ApiServices.getFilmsApiService
import com.app.batman.services.utilities.CheckRequest
import com.app.batman.services.utilities.ErrorHandler
import com.app.batman.utilities.LogicUtilityFunctions.toast
import kotlinx.coroutines.*

data class FilmsRepositories(
    val context: Context
) {
    val job: CompletableJob = Job()
    private val batmanDb = BatmanDb.getInstance(context)

    fun getFilms(): LiveData<MainPageDto?> {
        return object : LiveData<MainPageDto?>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(Dispatchers.IO + job).launch {
                        supervisorScope {
                            val call = async {
                                getFilmsApiService.getFilms(
                                    "3e974fca",
                                    "batman"
                                )
                            }
                            try {
                                withContext(Dispatchers.Main) {
                                    val res =
                                        CheckRequest.check(
                                            call.await(),
                                            context
                                        )
                                    res?.let {
                                        batmanDb.filmDao().insertFilms(res.films!!)
                                    }
                                    value = res
                                }
                            } catch (throwable: Throwable) {
                                Log.wtf("error", throwable.message)
                                withContext(Dispatchers.Main) {
                                    value = null
                                    context.toast(ErrorHandler.badRequest)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun getFilmDetail(imdbId: String): LiveData<Film?> {
        return object : LiveData<Film?>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(Dispatchers.IO + job).launch {
                        supervisorScope {
                            val call = async {
                                getFilmsApiService.getFilmDetail(
                                    "3e974fca",
                                    imdbId
                                )
                            }
                            try {
                                withContext(Dispatchers.Main) {
                                    val res =
                                        CheckRequest.check(
                                            call.await(),
                                            context
                                        )
                                    res?.let {
                                        batmanDb.filmDao().updateFilm(res)
                                    }
                                    value = res
                                }
                            } catch (throwable: Throwable) {
                                Log.wtf("error", throwable.message)
                                withContext(Dispatchers.Main) {
                                    value = null
                                    context.toast(ErrorHandler.badRequest)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun cancelJob() {
        job.cancel()
    }
}