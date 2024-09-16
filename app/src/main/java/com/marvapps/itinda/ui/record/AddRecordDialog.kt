package com.marvapps.itinda.ui.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marvapps.itinda.model.Record
import java.util.Date

@Composable
fun AddRecordDialog(
    modifier: Modifier = Modifier,
    submitted: (Record)->Unit,
    showDialog: Boolean = true,
    recordViewModel: RecordViewModel
){
    if(!showDialog)  return
    val recordTitle = recordViewModel.recordTitle
    val recordDate = recordViewModel.recordDate
    val scrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = {
            submitted(Record(
                title = "",
                date = Date()
            ))
        },
        confirmButton = {
            Button(onClick = {
                submitted(Record(
                    title = recordTitle,
                    date = recordDate
                ))
            }) {
                Text(text = "Submit")
            }
        },
        title = { Text("Add an entry") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ){
                TextField(
                    label = { Text(text="Title") },
                    value = recordTitle,
                    onValueChange = {recordViewModel.updateRecordTitle(it)},
                    modifier = Modifier
                        .height(50.dp)
                )

                TextField(
                    label = { Text(text="Date") },
                    value = "",
                    onValueChange = {},
                    modifier =
                    Modifier
                        .height(50.dp)
                )

            }
        }
    )
}

