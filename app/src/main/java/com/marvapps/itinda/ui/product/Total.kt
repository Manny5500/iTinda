package com.marvapps.itinda.ui.product

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
@Composable
fun Total(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel
){
    viewModel.calculateTotalPrice()
    val totalPrice  = viewModel.totalPrice
    val formattedPrice = String.format("₱ %.2f", totalPrice)
    Row(modifier = modifier.fillMaxWidth()
        .background(color = Color.White),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically){
        Text(text = "Total",
            modifier
                .padding(end = 10.dp)
                .wrapContentHeight())
        Text(text = formattedPrice,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFFF621E),
            modifier = modifier
                .padding(end = 16.dp)
                .wrapContentHeight()
        )
    }
}