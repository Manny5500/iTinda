package com.marvapps.itinda.ui.product

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _list = getProductItems().toMutableStateList()
    val list:List<ProductItem> get() = _list

    private val _showDialog = mutableStateOf(false)
    val showDialog: Boolean by _showDialog

    private val _name = mutableStateOf("")
    val name: String by _name

    private val _quantity = mutableStateOf("0")
    val quantity: String by _quantity

    private val _price = mutableStateOf("0.0")
    val price: String by _price

    private val _unit = mutableStateOf("")
    val unit: String by _unit

    private val _totalPrice = mutableStateOf(0.0)
    val totalPrice: Double by _totalPrice

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: Uri? by _imageUri

    private val _imagePath = mutableStateOf("")
    val imagePath: String by _imagePath

    fun closeItem(productItem: ProductItem){
        _list.remove(productItem)
    }

    fun checkedItem(productItem: ProductItem, isChecked: Boolean) =
        _list.find { it.id == productItem.id }?.let{item->
            item.isChecked = isChecked
        }

    fun addItem(productItem: ProductItem){
        val maxId = _list.maxOfOrNull{it.id}?:0
        _list.add(productItem.copy(id=maxId+1))
    }

    fun updateShowNew(){
        _showDialog.value = !_showDialog.value
    }

    fun updateName(name: String){
        _name.value = name
    }

    fun updateQuantity(quantity: String){
        _quantity.value = quantity
    }

    fun updatePrice(price: String){
        _price.value = price
    }

    fun updateUnit(unit: String){
        _unit.value = unit
    }

    fun updateImageUri(uri: Uri){
        _imageUri.value = uri
    }

    fun updateImagePath(string: String){
        _imagePath.value = string
    }

    fun calculatesTotalPrice(){
        var sum = 0.0
        for (productItem in _list) {
            sum += productItem.price
        }
        _totalPrice.value = sum
    }


    fun isProductItemValid(item: ProductItem): Boolean {
        return item.productName.isNotEmpty() &&
                item.quantity > 0 &&
                item.unit.isNotEmpty() &&
                item.price > 0.0 &&
                item.imageUri != null
    }
}

fun getProductItems() = List(0){
        i->
    ProductItem(
    id = i,
    productName = "Onion",
    quantity = 1,
    unit = "kilo",
    price = 1500.07)
}


data class ProductItem(
    var id: Int = 0,
    var quantity: Int = 0,
    var unit: String = "",
    var price: Double = 0.00,
    var productName: String = "",
    var initialChecked: Boolean = false,
    var imageUri: Uri? = null,
    var imagePath: String = ""
){
    var isChecked by mutableStateOf(initialChecked)
}
