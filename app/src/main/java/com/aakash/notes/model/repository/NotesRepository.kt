package com.aakash.notes.model.repository

import androidx.annotation.WorkerThread
import com.aakash.notes.model.data.Notes
import com.aakash.notes.model.database.NotesDao
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {

    val getAllNotes = notesDao.getAllNotes()

    @WorkerThread
    suspend fun insertNotes(notes: Notes) {
        notesDao.insertNotes(notes)
    }

    @WorkerThread
    suspend fun updateNotes(notes: Notes) {
        notesDao.updateNotes(notes)
    }

    @WorkerThread
    suspend fun deleteNote(notes: Notes) {
        notesDao.deleteNote(notes)
    }

    @WorkerThread
    suspend fun getIdData(id:Int):Notes {
        return notesDao.getIdData(id)
    }
    @WorkerThread
    suspend fun deleteData(id:Int) {
        notesDao.deleteById(id)
    }

    val countLiveData = notesDao.getCount()
}