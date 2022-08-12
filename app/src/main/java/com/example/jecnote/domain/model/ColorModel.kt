package com.example.jecnote.domain.model

import com.example.jecnote.data.database.model.ColorDbModel

data class ColorModel(
    val id:Long,
    val name:String,
    val hex:String
){
    companion object{
        val DEFALUT = with(ColorDbModel.DEFAULT_COLOR){ColorModel(id,name,hex)}
    }
}
