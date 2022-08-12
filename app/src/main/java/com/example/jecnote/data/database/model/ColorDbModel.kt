package com.example.jecnote.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ColorDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "hex") val hex: String,
    @ColumnInfo(name = "name") val name: String
) {
    companion object {
        val DEFAULT_COLORS = listOf(
            ColorDbModel(1, "#FFFFFF", "白"),
            ColorDbModel(2, "#E57373", "红"),
            ColorDbModel(3, "#F06292", "粉红"),
            ColorDbModel(4, "#CE93D8", "紫"),
            ColorDbModel(5, "#2196F3", "蓝"),
            ColorDbModel(6, "#00ACC1", "青"),
            ColorDbModel(7, "#26A69A", "蓝绿"),
            ColorDbModel(8, "#4CAF50", "绿"),
            ColorDbModel(9, "#8BC34A", "浅绿"),
            ColorDbModel(10, "#CDDC39", "酸橙"),
            ColorDbModel(11, "#FFEB3B", "黄"),
            ColorDbModel(12, "#FF9800", "桔"),
            ColorDbModel(13, "#BCAAA4", "棕"),
            ColorDbModel(14, "#9E9E9E", "灰")
        )
        val DEFAULT_COLOR = DEFAULT_COLORS[0]
    }
}
