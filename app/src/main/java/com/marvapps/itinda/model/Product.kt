package com.marvapps.itinda.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "quantity")var quantity: Int = 0,
    @ColumnInfo(name = "unit")var unit: String = "",
    @ColumnInfo(name = "price")var price: Double = 0.00,
    @ColumnInfo(name = "productName") var productName: String = "",
    @ColumnInfo(name = "initialChecked")var initialChecked: Boolean = false,
    @ColumnInfo(name = "imageUri")var imageUri: String = "",
    @ColumnInfo(name = "imagePath")var imagePath: String = "",
    @ColumnInfo(name = "recordId") var recordId: Int = 0
){
    var isChecked by mutableStateOf(initialChecked)
}
