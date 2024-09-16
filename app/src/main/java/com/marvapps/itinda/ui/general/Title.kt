package com.marvapps.itinda.ui.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Title(
    modifier: Modifier = Modifier,
    showNew: ()-> Unit,
    titleText: String = "eLista"){
    val customColor = Color(0xFF00BF63)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = customColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            color = Color.White,
            text = titleText,
            modifier = modifier
                .padding(8.dp)
                .wrapContentHeight(),
            style = MaterialTheme.typography.headlineMedium
        )
        IconButton(onClick = {showNew()},
            modifier = modifier.padding(start = 20.dp)) {
            Icon(Icons.Filled.Add, contentDescription = "add", tint = Color.White)
        }
    }
}