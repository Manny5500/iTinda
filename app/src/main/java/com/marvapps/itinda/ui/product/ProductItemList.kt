package com.marvapps.itinda.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marvapps.itinda.model.Product

@Composable
fun ProductItemList(
    modifier: Modifier = Modifier,
    list: List<Product>,
    onClose: (Product) -> Unit,
    onChecked: (Product, Boolean) -> Unit,
){
    val listState = rememberLazyListState()
    val max = list.maxOfOrNull { it.id }?:0
    LaunchedEffect(max) {
        listState.animateScrollToItem(max)
    }
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ){
        items(
            items = list,
            key = {task -> task.id}
        ){item ->
            ProductItem(
                productItem = item,
                onClose = {onClose(item)},
                onChecked = {checked->
                    onChecked(item, checked)
                },
                checked = item.isChecked
            )
        }
    }
}