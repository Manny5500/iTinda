package com.marvapps.itinda.ui.product
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.marvapps.itinda.ui.theme.EListaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : ComponentActivity() {
    private  val viewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recordId = intent.getIntExtra("recordId", 0)
        setContent {
            EListaTheme {
                ProductScreen(productViewModel = viewModel, recordId = recordId)
            }
        }
    }
}

