package com.marvapps.elista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marvapps.elista.ui.theme.EListaTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EListaTheme {
                FactoraScreen()
            }
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    showNew: ()-> Unit){
    Row(
        modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Factora",
            modifier = modifier.padding(start = 8.dp)
        )
        IconButton(onClick = {showNew()},
            modifier = modifier.padding(start = 20.dp)) {
            Icon(Icons.Filled.Add, contentDescription = "add")
        }
    }
}

@Composable
private fun New(
    modifier: Modifier = Modifier,
    submitted: (ProductItem)->Unit,
    viewModel: MainViewModel = viewModel()
){
    val name = viewModel.name
    val quantity = viewModel.quantity
    val price = viewModel.price
    val unit = viewModel.unit
    AlertDialog(
        onDismissRequest = {
            submitted(ProductItem())
        },
        confirmButton = {
            Button(onClick = {
                val productItem = ProductItem(
                    quantity = quantity.toIntOrNull()?:0,
                    price = price.toDoubleOrNull()?:0.0,
                    productName = name,
                    unit = unit
                )
                submitted(productItem)
            }) {
                Text(text = "Submit")
            }
        },
        title = {Text("Add an item")},
        text = {
            Column(modifier = modifier.fillMaxWidth()){
                TextField(
                    value = quantity,
                    onValueChange = {
                        viewModel.updateQuantity(it)
                    }
                )
                TextField(
                    value = unit,
                    onValueChange = {
                        viewModel.updateUnit(it)
                    }
                )

                TextField(
                    value = name,
                    onValueChange = {
                        viewModel.updateName(it)
                    }
                )

                TextField(
                    value = price,
                    onValueChange = {viewModel.updatePrice(it)}
                )
            }
        }
    )
}


@Composable
private fun FactoraItem(
    modifier: Modifier = Modifier,
    productItem: ProductItem,
    onClose: ()->Unit,
    onFavorite: (Boolean)->Unit,
    isFavorite: Boolean
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = {
                onFavorite(!isFavorite)
            },
            modifier = Modifier
        ) {
            Icon(
                Icons.Filled.Star, contentDescription = "favorite",
                tint = if (isFavorite) Color.Yellow else Color.Gray)
        }

        Text(
            text = productItem.quantity.toString(),
            modifier = modifier.padding(start = 12.dp)
        )
        Text(
            text = productItem.unit,
            modifier = modifier.padding(start = 16.dp)
        )
        Text(
            text = productItem.productName,
            modifier = modifier.padding(start = 16.dp)
        )
        Text(
            text = productItem.price.toString(),
            modifier = modifier.padding(start = 16.dp)
        )
        IconButton(
            onClick = {onClose()},
            modifier = modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "close"
            )
        }
    }
}


@Composable
private fun FactoraItemList(
    modifier: Modifier = Modifier,
    list: List<ProductItem> = remember{ getProductItems()},
    onClose: (ProductItem) -> Unit,
    onFavorite: (ProductItem, Boolean) -> Unit
){
    LazyColumn(modifier = modifier){
        items(
            items = list,
            key = {task -> task.id}
        ){item ->
            FactoraItem(
                productItem = item,
                onClose = {onClose(item)},
                onFavorite = {favorite->
                    onFavorite(item, favorite) },
                isFavorite = item.isFavorite
            )
        }
    }
}


@Composable
private fun FactoraScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
){
    Column(
        modifier = modifier
    ){
        val list = viewModel.list
        val showNew = viewModel.showNew
        var submitted by remember{
            mutableStateOf(false)
        }
        var productItem by remember {
            mutableStateOf(
                ProductItem(quantity = 1, productName = "", unit = "", price = 1.50)
            )
        }
        Title{
            viewModel.updateShowNew()
        }
        Total()
        FactoraItemList(
            list = list,
            onClose = {item->
                viewModel.closeItem(item)},
            onFavorite = {  item, favorite->
                viewModel.favoriteItem(item, favorite)
            }
        )
        if(showNew){
            New(
                submitted = {item->
                    productItem = item
                    submitted = true
                    viewModel.updateShowNew()
                }
            )
        }
        if(submitted){
            if(productItem.productName != "" && productItem.quantity > 0 &&
                productItem.unit != "" && productItem.price > 0.0){
                viewModel.addItem(productItem)
            }
            submitted = false
        }
    }
}

@Composable
fun Total(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
){
    val totalPrice  = viewModel.totalPrice
    Text(text = totalPrice.toString())
}