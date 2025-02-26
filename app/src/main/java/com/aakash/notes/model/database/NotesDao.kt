package com.aakash.notes.model.database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aakash.notes.model.data.Notes

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNotes(notes: Notes)

    @Update
    suspend fun updateNotes(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("select * from notes")
    fun getAllNotes(): LiveData<List<Notes>>

    @Query("select * from notes where nId= :id")
    suspend fun getIdData(id:Int):Notes

    @Query("delete from notes where nId= :id")
    suspend fun deleteById(id:Int)
}