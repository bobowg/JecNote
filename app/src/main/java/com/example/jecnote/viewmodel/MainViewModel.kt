package com.example.jecnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jecnote.data.repository.Repository
import com.example.jecnote.domain.model.NoteModel
import com.example.jecnote.routing.JetNotesRouter
import com.example.jecnote.routing.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
):ViewModel() {
    val notesNotInTrash:LiveData<List<NoteModel>> by lazy {
        repository.getAllNotesNotInTrash()
    }
    private var _noteEntry = MutableLiveData(NoteModel())
    val noteEntry:LiveData<NoteModel> = _noteEntry

    fun onCreateNewNoteClick() {
        JetNotesRouter.navigateTo(Screen.SaveNote)
    }

    fun onNoteClick(note: NoteModel) {
        // TODO - Open SaveNoteScreen in Edit mode
    }

    fun onNoteCheckedChange(note: NoteModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertNote(note)
        }
    }
}