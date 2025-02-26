package com.aakash.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aakash.notes.model.data.Notes
import com.aakash.notes.model.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    val getAllData = notesRepository.getAllNotes

    fun insertNotes(notes: Notes) {
        viewModelScope.launch {
            notesRepository.insertNotes(notes)
        }
    }

    fun updateNotes(notes: Notes){
        viewModelScope.launch {
            notesRepository.updateNotes(notes)
        }
    }

    fun deleteNotes(notes: Notes){
        viewModelScope.launch {
            notesRepository.deleteNote(notes)
        }
    }

    suspend fun getIdData(id:Int):Notes{
        return notesRepository.getIdData(id)
    }

    suspend fun deleteDataId(id:Int){
        notesRepository.deleteData(id)
    }

}