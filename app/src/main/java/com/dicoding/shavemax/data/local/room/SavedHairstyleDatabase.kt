package com.dicoding.shavemax.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SavedHairstyle::class],
    version = 1,
    exportSchema = false
)
abstract class SavedHairstyleDatabase : RoomDatabase() {
    abstract fun dao(): SavedHairstyleDao
    companion object {
        @Volatile
        private var INSTANCE: SavedHairstyleDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SavedHairstyleDatabase {
            if (INSTANCE == null) {
                synchronized(SavedHairstyleDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        SavedHairstyleDatabase::class.java, "savedhairstyle_db")
                        .build()
                }
            }
            return INSTANCE as SavedHairstyleDatabase
        }
    }
}