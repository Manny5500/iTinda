package com.marvapps.itinda.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.marvapps.itinda.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun deleteProduct(id: Int)

    @Query("SELECT * FROM product WHERE recordId = :recordId")
    suspend fun getAllProducts(recordId: Int): List<Product>

    @Query("SELECT * FROM product WHERE recordId = :recordId")
    fun getAllProductsNew(recordId: Int): Flow<List<Product>>



}