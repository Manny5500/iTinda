package com.marvapps.itinda.ui.record

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.marvapps.itinda.ui.general.Title
import com.marvapps.itinda.ui.product.ProductActivity

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel
){
    val context = LocalContext.current
    val list by viewModel.records.collectAsState()
    val showDialog = viewModel.showDialog

    Column {
        Title(showNew = {
            viewModel.updateShowDialog()
        }, modifier = modifier.height(60.dp))
        RecordItemList(
            recordList = list,
            onClicked = {recordId->
                goToProducts(recordId, context)
            },
            onClose = {record->
                viewModel.deleteRecord(record.id)
            },
            modifier = modifier
                .fillMaxHeight()
                .padding(bottom = 60.dp)
        )
        if(showDialog){
            AddRecordDialog(
                submitted = {record->
                    viewModel.updateShowDialog()
                    viewModel.insertRecord(record)
                },
                recordViewModel = viewModel
            )
        }
    }

}

fun goToProducts(id: Int, context: Context){
    val intent = Intent(context, ProductActivity::class.java).apply {
        putExtra("recordId", id)
    }
    context.startActivity(intent)
}

