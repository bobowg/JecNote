package com.example.jecnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jecnote.routing.Screen
import com.example.jecnote.ui.components.AppDrawer
import com.example.jecnote.ui.components.Note
import com.example.jecnote.ui.screens.NotesScreen
import com.example.jecnote.ui.theme.JecNoteTheme
import com.example.jecnote.viewmodel.MainViewModel
import com.example.jecnote.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

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
               NotesScreen(viewModel = viewModel)
            }
        }
    }
}
