package com.app.batman.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.batman.models.Film
import com.app.batman.utilities.Converter

@Database(entities = [Film::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class BatmanDb: RoomDatabase() {
    //Dao
    abstract fun filmDao(): FilmsDao

    companion object {
        @Volatile
        private lateinit var batmanDb: BatmanDb
        fun getInstance(context: Context): BatmanDb {
            if (!Companion::batmanDb.isInitialized)
                synchronized(this) {
                    if (!Companion::batmanDb.isInitialized) {
                        batmanDb = Room.databaseBuilder(
                            context.applicationContext,
                            BatmanDb::class.java,
                            "BatmanApp.db"
                        ).build()
                    }
                }
            return batmanDb
        }
    }
}
