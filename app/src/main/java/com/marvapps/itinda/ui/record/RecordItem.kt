package com.marvapps.itinda.ui.record

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marvapps.itinda.ui.theme.EListaTheme
import com.marvapps.itinda.model.Record
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecordItem(
    modifier: Modifier = Modifier,
    record: Record,
    onClose: ()->Unit,
    onClicked: (Int)->Unit
){
    Row(
        modifier = modifier.fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 8.dp)
            .clickable { onClicked(record.id)},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(text = record.title, modifier = modifier.weight(1f).padding(start = 8.dp))
        Text(text = formatDate(record.date), modifier = modifier.weight(1f))
        IconButton(onClick = {onClose()}, modifier = modifier.padding(start = 16.dp)) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "close"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordItemPreview(){
    EListaTheme {
        RecordItem(
            record = Record(
                date = Date(),
                title = "My Title"
            ),
            onClose = {},
            onClicked = {}
        )
    }
}

fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
    return sdf.format(date)
}

