package com.example.jecnote.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jecnote.data.database.dao.ColorDao
import com.example.jecnote.data.database.dao.NoteDao
import com.example.jecnote.data.database.model.ColorDbModel
import com.example.jecnote.data.database.model.NoteDbModel

@Database(entities = [NoteDbModel::class, ColorDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    companion object{
        const val DATABASE_NAME = "note-maker-database"
    }
    abstract fun noteDao():NoteDao
    abstract fun colorDao():ColorDao
}