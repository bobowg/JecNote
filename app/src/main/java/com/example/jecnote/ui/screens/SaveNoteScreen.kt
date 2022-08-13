package com.example.jecnote.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jecnote.R
import com.example.jecnote.domain.model.ColorModel
import com.example.jecnote.domain.model.NEW_NOTE_ID
import com.example.jecnote.domain.model.NoteModel
import com.example.jecnote.routing.JetNotesRouter
import com.example.jecnote.routing.Screen
import com.example.jecnote.ui.components.NoteColor
import com.example.jecnote.util.fromHex
import com.example.jecnote.viewmodel.MainViewModel

@Composable
fun SaveNoteScreen(
    viewModel: MainViewModel
) {
    val noteEntry: NoteModel by viewModel.noteEntry.observeAsState(NoteModel())
    Scaffold(
        topBar = {
            val isEditingMode: Boolean = noteEntry.id != NEW_NOTE_ID
            SaveNoteTopAppBar(
                isEditingMode = isEditingMode,
                onBackClick = { JetNotesRouter.navigateTo(Screen.Notes) },
                onSaveNoteClick = { /*TODO*/ },
                onOpenColorPickerClick = { /*TODO*/ },
                onDeleteNoteClick = {}
            )
        },
        content = {},
    )
}

@Composable
private fun SaveNoteTopAppBar(
    isEditingMode: Boolean,
    onBackClick: () -> Unit,
    onSaveNoteClick: () -> Unit,
    onOpenColorPickerClick: () -> Unit,
    onDeleteNoteClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "保存记事", color = MaterialTheme.colors.onPrimary)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Save Note ArrowBack",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = onSaveNoteClick) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = onOpenColorPickerClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_basline),
                    contentDescription = "Open Color Picker Button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            if (isEditingMode) {
                IconButton(onClick = onDeleteNoteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Note Button",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }

    )
}

@Preview
@Composable
private fun SaveNoteTopAppBarPrivew() {
    SaveNoteTopAppBar(
        isEditingMode = true,
        onBackClick = { /*TODO*/ },
        onSaveNoteClick = { /*TODO*/ },
        onOpenColorPickerClick = { /*TODO*/ },
        onDeleteNoteClick = {}
    )
}


@Composable
private fun ColorPicker(
    colors: List<ColorModel>,
    onColorSelect: (ColorModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "色彩面板",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(colors.size) { itemIndex ->
                val color = colors[itemIndex]
                ColorItem(color = color, onColorSelect = onColorSelect)
            }
        }
    }
}

@Preview
@Composable
fun ColorPickerPrivew() {
    ColorPicker(
        colors = listOf(ColorModel.DEFALUT, ColorModel.DEFALUT, ColorModel.DEFALUT),
        onColorSelect = {}
    )
}


@Composable
fun ColorItem(
    color: ColorModel,
    onColorSelect: (ColorModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onColorSelect(color)
            })
    ) {
        NoteColor(
            modifier = Modifier.padding(10.dp),
            color = Color.fromHex(color.hex),
            size = 80.dp,
            border = 2.dp
        )
        Text(
            text = color.name,
            fontSize = 22.sp,
            color = Color.fromHex(color.hex) ,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(alignment = Alignment.CenterVertically)
        )
    }

}

@Preview
@Composable
fun ColorItemPriew() {
    ColorItem(color = ColorModel.DEFALUT, onColorSelect = {})
}