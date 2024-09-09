package com.marvapps.elista

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _list = getProductItems().toMutableStateList()
    val list:List<ProductItem> get() = _list

    private val _showNew = mutableStateOf(false)
    val showNew: Boolean by _showNew

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

    fun closeItem(productItem: ProductItem){
        _list.remove(productItem)
        calculateTotalPrice()
    }

    fun favoriteItem(productItem: ProductItem, isFavorite: Boolean) =
        _list.find { it.id == productItem.id }?.let{item->
            item.isFavorite = isFavorite
        }

    fun addItem(productItem: ProductItem){
        val maxId = _list.maxOfOrNull{it.id}?:0
        _list.add(productItem.copy(id=maxId+1))
        calculateTotalPrice()
    }


    fun updateShowNew(){
        _showNew.value = !_showNew.value
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

    fun calculateTotalPrice(){
        var sum = 0.0
        for (productItem in _list) {
            sum += productItem.price
        }
        _totalPrice.value = sum
    }
}

fun getProductItems() = List(30){
        i->ProductItem(
    id = i,
    productName = "Onion",
    quantity = 1,
    unit = "kilo",
    price = 1500.00)
}

data class ProductItem(
    var id: Int = 0,
    var quantity: Int = 0,
    var unit: String = "",
    var price: Double = 0.0,
    var productName: String = "",
    var initialFavorite: Boolean = false
){
    var isFavorite by mutableStateOf(initialFavorite)
}