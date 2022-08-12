package com.example.jecnote.routing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

sealed class Screen{
    object Notes:Screen()
    object SaveNote:Screen()
    object Trash:Screen()
}

object JetNotesRouter {
    var currentScreen:Screen by mutableStateOf(Screen.Notes)

    fun navigateTo(destination:Screen){
        currentScreen = destination
    }
}
