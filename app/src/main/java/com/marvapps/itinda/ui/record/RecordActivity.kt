package com.marvapps.itinda.ui.record

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.marvapps.itinda.ui.theme.EListaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : ComponentActivity() {
    private val viewModel:RecordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EListaTheme {
                RecordScreen(viewModel = viewModel)
            }
        }
    }
}


