package com.marvapps.itinda.ui.record

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvapps.itinda.local.ProductDao
import com.marvapps.itinda.local.RecordDao
import com.marvapps.itinda.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date
import javax.inject.Inject
import com.marvapps.itinda.model.Record
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordDao: RecordDao,
    private val productDao: ProductDao

): ViewModel() {

    private val _showDialog = mutableStateOf(false)
    val showDialog:Boolean by _showDialog

    private val _recordTitle = mutableStateOf("")
    val recordTitle:String by _recordTitle

    private val _recordDate = mutableStateOf(Date())
    val recordDate:Date by _recordDate

    private val _records = MutableStateFlow<List<Record>>(emptyList())
    val records:StateFlow<List<Record>> = _records

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products:StateFlow<List<Product>> = _products

    fun updateShowDialog(){
        _showDialog.value = !_showDialog.value
    }

    fun updateRecordTitle(recordTitle: String){
        _recordTitle.value = recordTitle
    }

    fun updateRecordDate(recordDate: Date){
        _recordDate.value = recordDate
    }

    init{
        viewModelScope.launch{
            recordDao.getRecords().collect{
                _records.value = it
            }
        }


        viewModelScope.launch {
            productDao.getAllProductsNew(1).collect{
                _products.value = it
            }
        }
    }

    fun insertRecord(record:Record){
        viewModelScope.launch{
            if(isRecordValid(record))
                recordDao.insert(record)
        }
    }

    private fun isRecordValid(record: Record):Boolean{
        return record.title.isNotEmpty()
    }

    fun deleteRecord(id: Int){
        viewModelScope.launch{
            recordDao.deleteRecord(id)
            productDao.deleteProduct(id)
        }
    }




}