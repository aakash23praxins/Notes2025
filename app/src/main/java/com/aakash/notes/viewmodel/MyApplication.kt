package com.aakash.notes.viewmodel

import android.app.Application
import com.aakash.notes.model.database.NotesDatabase
import com.aakash.notes.model.repository.NotesRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()