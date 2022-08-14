package com.example.jecnote.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.jecnote.domain.model.NoteModel
import com.example.jecnote.routing.Screen
import com.example.jecnote.ui.components.AppDrawer
import com.example.jecnote.ui.components.Note
import com.example.jecnote.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(viewModel: MainViewModel) {
    val notes: List<NoteModel> by viewModel.notesNotInTrash.observeAsState(listOf())
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "记事本",
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Drawer Button"
                        )
                    }
                }
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Notes,
                closeDrawerAction = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          viewModel.onCreateNewNoteClick()
                },
                contentColor = MaterialTheme.colors.background,
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note Button")
                })
        },
        content = {
            if (notes.isNotEmpty()){
                NotesList(
                    notes = notes,
                    onNoteCheckedChange = { viewModel.onNoteCheckedChange(it) },
                    onNoteClick = { viewModel.onNoteClick(it) }
                )
            }
        }
    )

}

@Composable
private fun NotesList(
    notes: List<NoteModel>,
    onNoteCheckedChange: (NoteModel) -> Unit,
    onNoteClick: (NoteModel) -> Unit
) {
    LazyColumn {
        items(count = notes.size) { noteIndex ->
            val note = notes[noteIndex]
            Note(note = note, onNoteClick = onNoteClick, onNoteCheckedChange = onNoteCheckedChange, isSelected = false)
        }
    }
}

@Preview
@Composable
fun NotesListPrivew() {
    NotesList(notes = listOf(
        NoteModel(1,"Note 1","Content 1",null),
        NoteModel(2,"Note 2","Content 2",false),
        NoteModel(3,"Note 3","Content 3",true),
        NoteModel(4,"Note 4","Content 4",null),
        NoteModel(5,"Note 5","Content 5",null),
    ), onNoteCheckedChange ={} , onNoteClick ={} )
}
