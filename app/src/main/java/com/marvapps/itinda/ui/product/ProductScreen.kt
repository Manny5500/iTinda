package com.marvapps.itinda.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.marvapps.itinda.model.Product
import com.marvapps.itinda.ui.general.Title

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel,
    recordId:Int
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xfff8f8f8))
    ){

        val productList by productViewModel.products.collectAsState()
        LaunchedEffect(recordId){
            productViewModel.loadProducts(recordId)
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ){
            val showDialog = productViewModel.showDialog
            var submitted by remember{
                mutableStateOf(false)
            }
            var product by remember {
                mutableStateOf(Product())
            }

            Title(
                modifier = modifier
                .height(60.dp),
                showNew = {productViewModel.updateShowNew()}
            )

            ProductItemList(
                list = productList,
                onClose = {item->
                    productViewModel.deleteItem(item)
                          },
                onChecked = {item, checked->
                    productViewModel.checkedItem(item, checked)
                            },
                modifier = modifier
                    .fillMaxHeight()
                    .padding(bottom = 60.dp)
            )
            AddProductDialog(
                showNew = showDialog,
                viewModel = productViewModel,
                submitted = {item->
                    product = item
                    submitted = true
                    productViewModel.updateShowNew()
                },
                recordId = recordId
            )
            if(submitted){
                if(productViewModel.isProductValid(product)){
                    productViewModel.insert(product)
                }
                submitted = false
            }

        }
        Total(
            modifier = modifier
                .height(60.dp)
                .align(Alignment.BottomCenter),
            viewModel = productViewModel
        )
    }
}
