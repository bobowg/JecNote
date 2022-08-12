package com.example.jecnote.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jecnote.data.database.model.ColorDbModel

@Dao
interface ColorDao {
    @Query("SELECT * FROM colordbmodel")
    fun getAll():LiveData<List<ColorDbModel>>

    @Query("SELECT * FROM colordbmodel")
    fun getAllSync():List<ColorDbModel>

    @Query("SELECT * FROM colordbmodel WHERE id LIKE :id")
    fun findById(id:Long):LiveData<ColorDbModel>

    @Query("SELECT * FROM colordbmodel WHERE id LIKE :id")
    fun findByIdSync(id: Long):ColorDbModel

    @Insert
    fun insertAll(vararg colorDbModel: ColorDbModel)
}