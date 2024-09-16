package com.marvapps.itinda.ui.product

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marvapps.itinda.R
import com.marvapps.itinda.model.Product
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@Composable
fun AddProductDialog(
    modifier: Modifier = Modifier,
    submitted: (Product)->Unit,
    viewModel: ProductViewModel,
    showNew: Boolean = false,
    recordId: Int
){
    if(!showNew) return
    val maxNumber by viewModel.maxNumber.collectAsState()
    val name = viewModel.name
    val quantity = viewModel.quantity
    val price = viewModel.price
    val unit = viewModel.unit
    val scrollState = rememberScrollState()
    val imageUri = viewModel.imageUri
    val imagePath = viewModel.imagePath
    val context = LocalContext.current
    var filePath: File?

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            filePath = saveImageFromUri( context, uri, "image_$maxNumber")
            viewModel.updateImagePath(filePath.toString())
            viewModel.updateImageUri(uri)
        }
    }

    AlertDialog(
        onDismissRequest = {
            submitted(Product())
        },
        confirmButton = {
            Button(onClick = {
                val product = Product(
                    quantity = quantity.toIntOrNull()?:0,
                    price = price.toDoubleOrNull()?:0.0,
                    productName = name,
                    unit = unit,
                    imagePath = imagePath,
                    recordId = recordId
                )
                submitted(product)
            }) {
                Text(text = "Submit")
            }
        },
        title = { Text("Add an item") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ){
                TextField(
                    label = { Text(text="Add Image") },
                    value = if (imageUri.toString() == "null")
                        "" else imageUri.toString(),
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = { TextFieldLeadingIcon(imagePickerLauncher,imageUri)},
                    modifier = Modifier
                        .height(50.dp)
                )

                TextField(
                    label = { Text(text="Product Name") },
                    value = name,
                    onValueChange = {viewModel.updateName(it)},
                    modifier =
                    Modifier
                        .height(50.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    TextField(
                        label = { Text(text="Quantity") },
                        value = quantity,
                        onValueChange = {viewModel.updateQuantity(it)},
                        modifier =
                        Modifier
                            .weight(1f)
                            .height(50.dp)
                    )
                    TextField(
                        label = { Text(text="Unit") },
                        value = unit,
                        onValueChange = {viewModel.updateUnit(it)},
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    )
                }
                TextField(
                    label = { Text(text="Price") },
                    value = price,
                    onValueChange = {viewModel.updatePrice(it)},
                    modifier =
                    Modifier
                        .height(50.dp)
                        .clickable {}
                )
            }
        }
    )
}

@Composable
fun TextFieldLeadingIcon(imagePickerLauncher: ActivityResultLauncher<String>, imageUri: Uri?){
    Box(
        modifier = Modifier
            .size(45.dp)
            .clickable { imagePickerLauncher.launch("image/*") }
    ){
        if(imageUri!=null){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .placeholder(R.drawable.background_app)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }else{
            Image(
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun saveImageFromUri(context: Context, uri: Uri, fileName: String): File?{
    return try{
        val inputStream : InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.let {stream ->
            val bitmap: Bitmap = BitmapFactory.decodeStream(stream)
            val directory = context.filesDir
            val file = File(directory, fileName)

            FileOutputStream(file).use{outStream->
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, outStream)
                outStream.flush()
            }
            file
        }
    }catch(e: Exception){
        null
    }
}
