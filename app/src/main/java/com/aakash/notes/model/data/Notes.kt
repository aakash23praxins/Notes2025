package com.aakash.notes.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val nId: Int,
    val nTitle: String,
    val nDesc: String,
    val isFavourite: Boolean = false
)