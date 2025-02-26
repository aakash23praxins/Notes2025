package com.aakash.notes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aakash.notes.model.data.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NotesDao

    companion object {

        @Volatile
        var INSTANCE: NotesDatabase? = null

        fun getDatabaseInstance(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, NotesDatabase::class.java, "note_db")
                        .build()

                INSTANCE = instance
                instance
            }
        }
    }
}