package com.aakash.notes.viewmodel

import android.app.Application
import com.aakash.notes.model.database.NotesDatabase
import com.aakash.notes.model.repository.NotesRepository

class MyApplication : Application() {

    val database by lazy {
        NotesDatabase.getDatabaseInstance(this)
    }

    val repository by lazy {
        NotesRepository(database.noteDao())
    }
}