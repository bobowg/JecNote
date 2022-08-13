package com.example.jecnote.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.jecnote.domain.model.NoteModel
import com.example.jecnote.ui.components.Note
import com.example.jecnote.ui.components.TopAppBar
import com.example.jecnote.viewmodel.MainViewModel

@Composable
fun NotesScreen(viewModel: MainViewModel) {
    val notes: List<NoteModel> by viewModel.notesNotInTrash.observeAsState(listOf())
    Column {
        TopAppBar(title = "记事本", icon = Icons.Default.List, onIconClick = {})
        NotesList(
            notes = notes,
            onNoteCheckedChange = { viewModel.onNoteCheckedChange(it) },
            onNoteClick = { viewModel.onNoteClick(it) }
        )
    }
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
            Note(note = note, onNoteClick = onNoteClick, onNoteCheckedChange = onNoteCheckedChange)
        }
    }
}

@Preview
@Composable
fun NotesListPrivew() {
    NotesList(notes = listOf(
        NoteModel(1,"Note 1","Content 1",null),
        NoteModel(2,"Note 2","Content 2",null),
        NoteModel(3,"Note 3","Content 3",null),
        NoteModel(4,"Note 4","Content 4",null),
        NoteModel(5,"Note 5","Content 5",null),
    ), onNoteCheckedChange ={} , onNoteClick ={} )
}
