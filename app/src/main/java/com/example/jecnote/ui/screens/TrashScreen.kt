package com.example.jecnote.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jecnote.R
import com.example.jecnote.domain.model.NoteModel
import com.example.jecnote.routing.Screen
import com.example.jecnote.ui.components.AppDrawer
import com.example.jecnote.ui.components.Note
import com.example.jecnote.viewmodel.MainViewModel
import kotlinx.coroutines.launch

private const val NO_DIALOG = 1
private const val RESTORE_NOTES_DIALOG = 2
private const val PERMANENTLY_DELETE_DIALOG = 3

@Composable
fun TrashScreen(
    viewModel: MainViewModel
) {
    val notesInThrash: List<NoteModel> by viewModel.notesInTrash.observeAsState(listOf())
    val selectedNotes: List<NoteModel> by viewModel.selectedNotes.observeAsState(listOf())
    val dialogState: MutableState<Int> = rememberSaveable { mutableStateOf(NO_DIALOG) }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            val areActionsVisible = selectedNotes.isNotEmpty()
            TrashTopAppBar(
                onNavigationIconClick = { coroutineScope.launch { scaffoldState.drawerState.open() } },
                onRestoreNotesClick = { dialogState.value = RESTORE_NOTES_DIALOG },
                onDeleteNotesClick = { dialogState.value = PERMANENTLY_DELETE_DIALOG },
                areActionsVisible = areActionsVisible
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Trash,
                closeDrawerAction = {
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                }
            )
        },
        content = {
            Content(
                notes = notesInThrash,
                onNoteClick = { viewModel.onNoteSelected(it) },
                selectedNotes = selectedNotes
            )
            val dialog = dialogState.value
            if (dialog != NO_DIALOG) {
                val confirmAction: () -> Unit = when (dialog) {
                    RESTORE_NOTES_DIALOG -> {
                        {
                            viewModel.restoreNotes(selectedNotes)
                            dialogState.value = NO_DIALOG
                        }
                    }
                    PERMANENTLY_DELETE_DIALOG -> {
                        {
                            viewModel.permanentlyDeleteNotes(selectedNotes)
                            dialogState.value = NO_DIALOG
                        }
                    }
                    else -> {
                        {
                            dialogState.value = NO_DIALOG
                        }
                    }
                }

                AlertDialog(
                    onDismissRequest = { dialogState.value = NO_DIALOG },
                    title = { Text(mapDialogTitle(dialog)) },
                    text = { Text(mapDialogText(dialog)) },
                    confirmButton = {
                        TextButton(onClick = confirmAction) {
                            Text("断续")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { dialogState.value = NO_DIALOG }) {
                            Text("取消")
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun TrashTopAppBar(
    onNavigationIconClick: () -> Unit,
    onRestoreNotesClick: () -> Unit,
    onDeleteNotesClick: () -> Unit,
    areActionsVisible: Boolean
) {
    TopAppBar(
        title = {
            Text(text = "垃圾箱", color = MaterialTheme.colors.onPrimary)
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Default.List, contentDescription = "Drawer Button")
            }
        },
        actions = {
            if (areActionsVisible) {
                IconButton(onClick = onRestoreNotesClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bastrash),
                        contentDescription = "Restore Notes Trash",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                IconButton(onClick = onDeleteNotesClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_basdelete),
                        contentDescription = "Delete Notes Trash",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    )

}

@Preview
@Composable
fun TrashTopAppBarPrivew() {
    TrashTopAppBar(
        onNavigationIconClick = { /*TODO*/ },
        onRestoreNotesClick = { /*TODO*/ },
        onDeleteNotesClick = { /*TODO*/ },
        areActionsVisible = false
    )
}

@Composable
private fun Content(
    notes: List<NoteModel>,
    onNoteClick: (NoteModel) -> Unit,
    selectedNotes: List<NoteModel>
) {
    val tabs = listOf("常规", "检查")
    var selectedTab by remember { mutableStateOf(0) }
    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title) }
                )
            }
        }
        val filteredNotes = when (selectedTab) {
            0 -> {
                notes.filter { it.isCheckedOff == null }
            }
            1 -> {
                notes.filter { it.isCheckedOff != null }
            }
            else -> {
                throw IllegalStateException("Tab not supported - index:$selectedTab")
            }
        }
        LazyColumn {
            items(count = filteredNotes.size) { noteIndex ->
                val note = filteredNotes[noteIndex]
                val isNoteSelected = selectedNotes.contains(note)
                Note(note = note, onNoteClick = onNoteClick, isSelected = isNoteSelected)
            }
        }
    }
}

@Preview
@Composable
fun ContentPriew() {
    Content(
        notes =
        listOf(
            NoteModel(1, "title1", "content1"),
            NoteModel(2, "title2", "content2"),
            NoteModel(3, "title3", "content3"),
            NoteModel(4, "title4", "content4")
        ),
        onNoteClick = {},
        selectedNotes = listOf(
            NoteModel(1, "title1", "content1"),
            NoteModel(2, "title2", "content2")
        )
    )
}

private fun mapDialogTitle(dialog: Int): String = when (dialog) {
    RESTORE_NOTES_DIALOG -> "回复记事"
    PERMANENTLY_DELETE_DIALOG -> "永久删除"
    else -> throw RuntimeException("Dialog not supported:$dialog")
}

private fun mapDialogText(dialog: Int): String = when (dialog) {
    RESTORE_NOTES_DIALOG -> "您确定要恢复选定的笔记吗？"
    PERMANENTLY_DELETE_DIALOG -> "您确定要永久删除选定的笔记吗？"
    else -> throw RuntimeException("Dialog not supported:$dialog")
}