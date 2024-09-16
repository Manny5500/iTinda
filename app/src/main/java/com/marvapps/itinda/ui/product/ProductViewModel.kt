package com.marvapps.itinda.ui.product

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvapps.itinda.model.Product
import com.marvapps.itinda.local.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productDao: ProductDao
):ViewModel() {

    fun insert(product: Product) = viewModelScope.launch{
        try {
            productDao.insert(product)
            Log.d("MyApp", "Success")
        }catch (e:Exception){
            Log.e("MyApp", "Failed to insert $e")
        }
    }

    fun checkedItem(product: Product, isChecked: Boolean) = viewModelScope.launch{
        try {
            val checkedProduct = product.copy(
                initialChecked = isChecked
            )
            productDao.update(checkedProduct)
        }catch (e:Exception){
            Log.e("MyApp", "Failed to update $e")
        }
    }

    fun deleteItem(product: Product) = viewModelScope.launch {
        try{
            productDao.deleteProduct(product.id)
        }catch(e: Exception){
            Log.e("MyApp", "Failed to delete $e")
        }
    }

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun loadProducts(recordId: Int){
        viewModelScope.launch{
            productDao.getAllProductsNew(recordId).collect{productList->
                _products.value = productList
            }
        }
        viewModelScope.launch {
            products
                .map { productList -> productList.maxOfOrNull { it.id } ?: 0 }
                .collect { maxNumber ->
                    _maxNumber.value = maxNumber
                }
        }
    }

    private val _maxNumber = MutableStateFlow(0)
    val maxNumber:StateFlow<Int> = _maxNumber


    fun isProductValid(product: Product): Boolean {
        return product.productName.isNotEmpty() &&
                product.quantity > 0 &&
                product.unit.isNotEmpty() &&
                product.price > 0.0 &&
                product.imagePath.isNotEmpty()
    }

    private val _totalPrice = mutableStateOf(0.0)
    val totalPrice: Double by _totalPrice

    fun calculateTotalPrice(){
        var sum = 0.0
        val list = _products.value
        for (productItem in list) {
            sum += productItem.price
        }
        _totalPrice.value = sum
    }

    private val _showDialog = mutableStateOf(false)
    val showDialog: Boolean by _showDialog

    fun updateShowNew(){
        _showDialog.value = !_showDialog.value
    }

    private val _name = mutableStateOf("")
    val name: String by _name

    private val _quantity = mutableStateOf("0")
    val quantity: String by _quantity

    private val _price = mutableStateOf("0.0")
    val price: String by _price

    private val _unit = mutableStateOf("")
    val unit: String by _unit

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: Uri? by _imageUri

    private val _imagePath = mutableStateOf("")
    val imagePath: String by _imagePath

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

}

/*
class ProductViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/