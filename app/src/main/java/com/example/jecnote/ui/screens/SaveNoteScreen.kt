package com.example.jecnote.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SaveNoteScreen(
    viewModel: MainViewModel
) {
    val noteEntry: NoteModel by viewModel.noteEntry.observeAsState(NoteModel())
    val colors: List<ColorModel> by viewModel.colors.observeAsState(listOf())
    val bottomDrawerState: BottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val moveNoteToTrashDialogShownState: MutableState<Boolean> = rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            val isEditingMode: Boolean = noteEntry.id != NEW_NOTE_ID
            SaveNoteTopAppBar(
                isEditingMode = isEditingMode,
                onBackClick = { JetNotesRouter.navigateTo(Screen.Notes) },
                onSaveNoteClick = {
                                  viewModel.saveNote(noteEntry)
                },
                onOpenColorPickerClick = {
                    coroutineScope.launch { bottomDrawerState.open() }
                },
                onDeleteNoteClick = {
                    moveNoteToTrashDialogShownState.value = true
                }
            )
        },

        content = {
            BottomDrawer(
                drawerState = bottomDrawerState,
                drawerContent = {
                    ColorPicker(colors = colors, onColorSelect = { color ->
                        val newNoteEntry = noteEntry.copy(color = color)
                        viewModel.onNoteEntryChange(newNoteEntry)
                    })
                },
                content = {
                    SaveNoteContent(note = noteEntry, onNoteChange = { updateNoteEntry ->
                        viewModel.onNoteEntryChange(updateNoteEntry)
                    })
                }
            )
            if (moveNoteToTrashDialogShownState.value) {
                AlertDialog(
                    onDismissRequest = { moveNoteToTrashDialogShownState.value = false },
                    title = { Text(text = "把纸条移到垃圾桶") },
                    text = {
                        Text(text = "你确定要把这张纸条移到垃圾箱？")
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.moveNoteToTrash(noteEntry) }) {
                            Text(text = "继续吗？")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            moveNoteToTrashDialogShownState.value = false
                        }) {
                            Text(text = "取消")
                        }
                    }
                )
            }
        }
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

@Composable
private fun NoteCheckOption(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .padding(top = 16.dp)
    ) {
        Text(text = "勾选删除笔记？", modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun NoteCheckOptionPrivew() {
    NoteCheckOption(isChecked = true, onCheckedChange = {})
}

@Composable
private fun ContentTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    onTextChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        )
    )
}

@Preview
@Composable
private fun ContentTextFieldPrivew() {
    ContentTextField(label = "Title", text = "", onTextChange = {})
}

@Composable
private fun SaveNoteContent(
    note: NoteModel,
    onNoteChange: (NoteModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ContentTextField(label = "标题", text = note.title, onTextChange = { newTitle ->
            onNoteChange.invoke(note.copy(title = newTitle))
        })
        ContentTextField(label = "正文", text = note.content, onTextChange = { newContent ->
            onNoteChange.invoke(note.copy(content = newContent))
        })

        val canBeCheckedOff: Boolean = note.isCheckedOff != null
        NoteCheckOption(isChecked = canBeCheckedOff, onCheckedChange = { canBeCheckedOffNewValue ->
            val isCheckedOff: Boolean? = if (canBeCheckedOffNewValue) false else null
            onNoteChange.invoke(note.copy(isCheckedOff = isCheckedOff))
        })
        PickedColor(color = note.color)
    }
}

@Composable
fun PickedColor(color: ColorModel) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .padding(top = 16.dp)
    ) {
        Text(
            text = "色彩面板",
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        )
        NoteColor(
            color = Color.fromHex(color.hex),
            size = 40.dp,
            border = 1.dp,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview
@Composable
fun SaveNoteContentPrivew() {
    SaveNoteContent(note = NoteModel(1, "title", "content"), onNoteChange = {})
}
