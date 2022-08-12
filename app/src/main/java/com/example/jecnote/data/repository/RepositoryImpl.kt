package com.example.jecnote.data.repository

import androidx.lifecycle.LiveData
import com.example.jecnote.data.database.dao.ColorDao
import com.example.jecnote.data.database.dao.NoteDao
import com.example.jecnote.data.database.dbmapper.DbMapper
import com.example.jecnote.domain.model.ColorModel
import com.example.jecnote.domain.model.NoteModel

class RepositoryImpl(
    private val noteDao: NoteDao,
    private val colorDao: ColorDao,
    private val dbMapper: DbMapper
):Repository {
    override fun getAllNotesNotInTrash(): LiveData<List<NoteModel>> {
        TODO("Not yet implemented")
    }

    override fun getALLNotesInTrash(): LiveData<List<NoteModel>> {
        TODO("Not yet implemented")
    }

    override fun getNote(id: Long): LiveData<NoteModel> {
        TODO("Not yet implemented")
    }

    override fun insertNote(note: NoteModel) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(id: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteNotes(noteIds: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun moveNoteToTrash(noteId: Long) {
        TODO("Not yet implemented")
    }

    override fun restoreNotesFromTrash(noteIds: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun getAllColors(): LiveData<List<ColorModel>> {
        TODO("Not yet implemented")
    }

    override fun getAllColorsSync(): List<ColorModel> {
        TODO("Not yet implemented")
    }

    override fun getColor(id: Long): LiveData<ColorModel> {
        TODO("Not yet implemented")
    }

    override fun getColorSync(id: Long): ColorModel {
        TODO("Not yet implemented")
    }
}