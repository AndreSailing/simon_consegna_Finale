package com.example.simon_consegna_intermedia.ui.SecondActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.simon_consegna_intermedia.ui.components.Screen
import com.example.simon_consegna_intermedia.ui.functions.GameViewModel
import com.example.simon_consegna_intermedia.ui.theme.Simon_Consegna_IntermediaTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class GameActivity : ComponentActivity() {
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            viewModel= viewModel()


            Simon_Consegna_IntermediaTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    Screen(Modifier.Companion.padding(innerPadding),viewModel) { partita ->
                        val resultIntent = Intent()
                        resultIntent.putExtra("partita", partita)
                        resultIntent.putExtra("ErrorIndexPartita",viewModel.getErrorIndex())
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }()

                }
            }
        }
    }
    override fun finish() {
        val data = Intent().apply {
            putExtra("partita", viewModel.getPartita())
            putExtra("ErrorIndexPartita",viewModel.getErrorIndex())

        }
        setResult(RESULT_OK, data)
        super.finish()
    }
}