package com.marvapps.itinda.ui.product

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marvapps.itinda.R
import com.marvapps.itinda.model.Product

@SuppressLint("DefaultLocale")
@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    productItem: Product,
    onClose: ()->Unit,
    checked: Boolean,
    onChecked: (Boolean)->Unit
){
    Row(
        modifier = modifier.fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Checkbox(
            checked = checked,
            onCheckedChange = onChecked,
            modifier = Modifier.padding(start = 8.dp)
        )
        val context = LocalContext.current
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(Uri.parse(productItem.imagePath))
                .placeholder(R.drawable.background_app)
                .error(R.drawable.background_app)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier =  Modifier.size(65.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFDEDEDE), RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )


        val formattedPrice = String.format("₱ %.2f", productItem.price)
        Column(
            modifier = Modifier.padding(start= 8.dp)
                .weight(1f)
        ){
            Text(
                text = productItem.productName,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${productItem.quantity}  ${productItem.unit}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF3a3a3a)
                ),
                modifier = Modifier
                    .background(color = Color(0xFFF8f8f8), shape = RoundedCornerShape(8.dp))
                    .padding(3.dp)
            )
            Text(
                text = formattedPrice,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF621E)
                )
            )
        }
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


