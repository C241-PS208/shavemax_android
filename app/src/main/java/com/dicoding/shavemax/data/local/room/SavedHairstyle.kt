package com.dicoding.shavemax.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SavedHairstyle(
    @PrimaryKey(autoGenerate = false)
    var name: String = "",
    var description: String,
    var photoUrl: String
)