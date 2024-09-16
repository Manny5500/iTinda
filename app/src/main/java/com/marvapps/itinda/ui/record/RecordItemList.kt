package com.marvapps.itinda.ui.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import com.marvapps.itinda.ui.theme.EListaTheme
import com.marvapps.itinda.model.Record
import java.util.Date

@Composable
fun RecordItemList(
    modifier: Modifier = Modifier,
    recordList: List<Record>,
    onClose: (Record)->Unit,
    onClicked: (Int)->Unit
){
    val listState = rememberLazyListState()
    val max = recordList.maxOfOrNull { it.id }?:0
    LaunchedEffect(max) {
        listState.animateScrollToItem(max)
    }
    LazyColumn (
        modifier = modifier.fillMaxWidth(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(1.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ){
        items(
            items = recordList,
            key = {task -> task.id}
        ){item->
            RecordItem(
                record = item,
                onClose = {onClose(item)},
                onClicked = {onClicked(item.id)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordItemListPreview(){
    val list = List(8){i->
       Record(
            title = "My Record $i",
            id = i,
            date = Date()
       )
    }
    EListaTheme {
        RecordItemList(
            onClicked = {},
            onClose = {},
            recordList = list
        )
    }
}
