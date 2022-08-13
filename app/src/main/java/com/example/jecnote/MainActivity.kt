package com.example.jecnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.jecnote.routing.JetNotesRouter
import com.example.jecnote.routing.Screen
import com.example.jecnote.ui.screens.NotesScreen
import com.example.jecnote.ui.screens.SaveNoteScreen
import com.example.jecnote.ui.screens.TrashScreen
import com.example.jecnote.ui.theme.JecNoteTheme
import com.example.jecnote.viewmodel.MainViewModel
import com.example.jecnote.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels(factoryProducer = {
        MainViewModelFactory(
            this,
            (application as JetNotesApplication).dependencyInjector.repository
        )
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JecNoteTheme {
               MainActivityScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun MainActivityScreen(
    viewModel: MainViewModel
) {
    Surface {
        when(JetNotesRouter.currentScreen){
            is Screen.Notes -> NotesScreen(viewModel = viewModel)
            is Screen.SaveNote -> SaveNoteScreen(viewModel = viewModel)
            is Screen.Trash -> TrashScreen(viewModel)
        }
    }
}
