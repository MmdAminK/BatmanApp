package com.app.batman.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.app.batman.repositories.FilmsRepositories
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainPageViewModel(application: Application) : AndroidViewModel(application) {
    var page: Int = 0
    private val repo = FilmsRepositories(application)
    val result = repo.getFilms()

    fun cancelJob() {
        repo.cancelJob()
    }
}