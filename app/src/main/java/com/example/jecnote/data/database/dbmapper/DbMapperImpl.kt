package com.example.jecnote.data.database.dbmapper

import com.example.jecnote.data.database.model.ColorDbModel
import com.example.jecnote.data.database.model.NoteDbModel
import com.example.jecnote.domain.model.ColorModel
import com.example.jecnote.domain.model.NoteModel

class DbMapperImpl:DbMapper {
    override fun mapNotes(
        noteDbModels: List<NoteDbModel>,
        colorDbModels: Map<Long, ColorDbModel>
    ): List<NoteModel> = noteDbModels.map {
        val colorDbModel = colorDbModels[it.colorId]
            ?: throw RuntimeException("Color for colorId: ${it.colorId} was not found. Make sure that all colors are passed to this method")

        mapNote(it, colorDbModel)
    }

    override fun mapNote(noteDbModel: NoteDbModel, colorDbModel: ColorDbModel): NoteModel {
        TODO("Not yet implemented")
    }

    override fun mapColors(colorDbModels: List<ColorDbModel>): List<ColorModel> {
        TODO("Not yet implemented")
    }

    override fun mapColor(colorDbModel: ColorDbModel): ColorModel {
        TODO("Not yet implemented")
    }

    override fun mapDbNote(note: NoteModel): NoteDbModel {
        TODO("Not yet implemented")
    }

    override fun mapDbColors(colors: List<ColorModel>): List<ColorDbModel> {
        TODO("Not yet implemented")
    }

    override fun mapDbColor(color: ColorModel): ColorDbModel {
        TODO("Not yet implemented")
    }

}