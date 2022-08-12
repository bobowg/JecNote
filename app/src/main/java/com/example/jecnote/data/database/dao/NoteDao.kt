package com.example.jecnote.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jecnote.data.database.model.NoteDbModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM notedbmodel")
    fun getAllSync():List<NoteDbModel>

    @Query("SELECT * FROM notedbmodel WHERE id IN (:noteIds)")
    fun getNotesByIdsSync(noteIds:List<Long>):List<NoteDbModel>

    @Query("SELECT * FROM notedbmodel WHERE id LIKE :id")
    fun findById(id:Long):NoteDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(noteDbModel: NoteDbModel)

    @Insert
    fun insertAll(vararg noteDbModel: NoteDbModel)

    @Query("DELETE FROM notedbmodel WHERE id LIKE :id")
    fun delete(id: Long)

    @Query("DELETE FROM notedbmodel WHERE id IN (:noteIds)")
    fun delete(noteIds: List<Long>)
}