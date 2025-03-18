package com.aakash.notes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aakash.notes.model.data.Notes

@Database(entities = [Notes::class], version = 3)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NotesDao

    companion object {

        val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE notes ADD COLUMN createdAt INTEGER NOT NULL DEFAULT(0)")
            }
        }

        val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. Create a new temporary table without the isFavourite column
                database.execSQL(
                    """
            CREATE TABLE notes_new (
                nId INTEGER PRIMARY KEY NOT NULL,
                nTitle TEXT NOT NULL,
                nDesc TEXT NOT NULL,
                createdAt INTEGER NOT NULL
            )
        """.trimIndent()
                )

                // 2. Copy the data from the old table to the new table
                database.execSQL(
                    """
            INSERT INTO notes_new (nId, nTitle, nDesc, createdAt)
            SELECT nId, nTitle, nDesc, createdAt FROM notes
        """.trimIndent()
                )

                // 3. Drop the old table
                database.execSQL("DROP TABLE notes")

                // 4. Rename the new table to the old table's name
                database.execSQL("ALTER TABLE notes_new RENAME TO notes")
            }
        }

        @Volatile
        var INSTANCE: NotesDatabase? = null

        fun getDatabaseInstance(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, NotesDatabase::class.java, "note_db")
                        .addMigrations(migration_1_2, migration_2_3)
                        .build()

                INSTANCE = instance
                instance
            }
        }
    }
}