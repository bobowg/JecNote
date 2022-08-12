package com.example.jecnote.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jecnote.routing.JetNotesRouter
import com.example.jecnote.routing.Screen
import com.example.jecnote.ui.theme.JecNoteTheme
import com.example.jecnote.ui.theme.JetNotesThemeSettings


@Composable
fun AppDrawer(
    currentScreen: Screen,
    closeDrawerAction: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppDrawerHeader()
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f))
        ScreenNavigationButton(
            icon = Icons.Default.Home,
            label = "记事本",
            isSelected = currentScreen == Screen.Notes,
            onClick = {
                JetNotesRouter.navigateTo(Screen.Notes)
                closeDrawerAction()
            }
        )
        ScreenNavigationButton(
            icon = Icons.Default.Delete,
            label = "垃圾",
            isSelected = currentScreen == Screen.Trash,
            onClick = {
                JetNotesRouter.navigateTo(Screen.Trash)
                closeDrawerAction()
            })
        LightDarkThemeItem()
    }
}

@Preview
@Composable
private fun AppDrawerPrivew() {
    AppDrawer(Screen.Notes, {})
}

@Composable
private fun LightDarkThemeItem() {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "暗",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .align(alignment = Alignment.CenterVertically)
        )
        Switch(
            checked = JetNotesThemeSettings.isDarkThemeEnable,
            onCheckedChange = { JetNotesThemeSettings.isDarkThemeEnable = it },
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .align(alignment = Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun LightDarkThemeItemPrivew() {
    JecNoteTheme {
        LightDarkThemeItem()
    }
}


@Composable
private fun ScreenNavigationButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = MaterialTheme.colors
    val imageAlpha = if (isSelected) 1f else 0.6f
    val textColor = if (isSelected) color.primary else color.onSurface.copy(alpha = 0.6f)
    val backgroundColr = if (isSelected) color.primary.copy(alpha = 0.12f) else color.surface

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = backgroundColr,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onClick
                )
                .padding(4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = icon,
                contentDescription = "Screen Navigation Button",
                colorFilter = ColorFilter.tint(textColor),
                alpha = imageAlpha
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                color = textColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun ScreenNavigationButtonPrivew() {
    JecNoteTheme {
        ScreenNavigationButton(
            icon = Icons.Default.Home,
            label = "Notes",
            isSelected = true,
            onClick = {})
    }
}

@Composable
private fun AppDrawerHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            imageVector = Icons.Default.Menu,
            contentDescription = "Drawer Header Ico",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "记事本",
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
private fun AppDrawerHeaderPrivew() {
    JecNoteTheme {
        AppDrawerHeader()
    }
}