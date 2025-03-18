package com.aakash.notes.model.di

import android.content.Context
import com.aakash.notes.model.database.NotesDao
import com.aakash.notes.model.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getDatabaseInstance(context)
    }

    @Provides
    @Singleton
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao {
        return notesDatabase.noteDao()
    }
}