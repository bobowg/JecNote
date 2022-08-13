package com.example.jecnote.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.jecnote.data.database.AppDatabase
import com.example.jecnote.data.database.dbmapper.DbMapper
import com.example.jecnote.data.database.dbmapper.DbMapperImpl
import com.example.jecnote.data.repository.Repository
import com.example.jecnote.data.repository.RepositoryImpl

class DependencyInjector(applicationContext: Context) {

    val repository:Repository by lazy { provideRepository(database) }

    private val dbMapper:DbMapper = DbMapperImpl()
    private val database:AppDatabase by lazy { provideDatabase(applicationContext) }

    private fun provideDatabase(applicationContext: Context):AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    private fun provideRepository(database: AppDatabase):Repository {
        val noteDao = database.noteDao()
        val colorDao = database.colorDao()
        return RepositoryImpl(noteDao,colorDao, dbMapper)
    }
}