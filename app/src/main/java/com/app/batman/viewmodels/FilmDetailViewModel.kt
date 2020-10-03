package com.app.batman.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.batman.repositories.FilmsRepositories
import com.app.batman.utilities.LogicUtilityFunctions.checkMutableLiveData

class FilmDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var mutableLiveData = MutableLiveData<String>()
    private val repo = FilmsRepositories(application)

    val result = Transformations.switchMap(mutableLiveData) {
        repo.getFilmDetail(it)
    }

    fun inputData(input: Any): Boolean {
        return mutableLiveData.checkMutableLiveData(input as String)
    }

    fun cancelJob() {
        repo.cancelJob()
    }
}