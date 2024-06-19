package com.dicoding.hairstyler.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedHairstyleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(hairstyle: SavedHairstyle)

    @Delete
    fun delete(hairstyle: SavedHairstyle)

    @Query("SELECT * from savedhairstyle")
    fun getSavedHairstyles(): List<SavedHairstyle>

    @Query("SELECT * FROM savedhairstyle WHERE name = :name")
    fun getSavedHairstyleByName(name: String): LiveData<SavedHairstyle>
}