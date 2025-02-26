package com.aakash.notes.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aakash.notes.model.repository.NotesRepository

class NotesViewModelFactory(private val repository: NotesRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(repository) as T
        }else{
            throw IllegalArgumentException("viewmodel not found")
        }
    }

}