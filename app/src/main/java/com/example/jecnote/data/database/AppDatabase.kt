package com.example.jecnote.data.database

import androidx.room.Database
import com.example.jecnote.data.database.model.ColorDbModel
import com.example.jecnote.data.database.model.NoteDbModel

@Database(entities = [NoteDbModel::class, ColorDbModel::class], version = 1)
abstract class AppDatabase {

}