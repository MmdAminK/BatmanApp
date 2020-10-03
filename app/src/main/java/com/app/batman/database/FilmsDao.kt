package com.app.batman.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.app.batman.models.Film

@Dao
interface FilmsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilms(films: List<Film>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFilm(film: Film)

    @Query("SELECT * FROM Film")
    suspend fun findFilms(): List<Film>

    @Query("SELECT * FROM Film WHERE imdbID = :id")
    suspend fun findFilmDetails(id: String): Film

    @Query("DELETE FROM Film")
    suspend fun deleteFilms()
}