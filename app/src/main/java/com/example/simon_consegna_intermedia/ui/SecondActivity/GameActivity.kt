package com.example.simon_consegna_intermedia.ui.SecondActivity

import android.content.Intent
import android.media.SoundPool
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
import generateToneWavFile
import java.io.File

class GameActivity : ComponentActivity() {
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val suoni = mapOf(
            "R" to 330,
            "G" to 415,
            "B" to 494,
            "Y" to 262,
            "M" to 370,
            "C" to 523,
            "E" to 110
        )

        suoni.forEach { (nome, freq) ->
            generateToneWavFile(this, freq, 300, nome)
        }
        val soundPool = SoundPool.Builder()
            .setMaxStreams(7)
            .build()

        val soundMap = mutableMapOf<String, Int>()

        suoni.keys.forEach { nome ->
            val file = File(filesDir, "$nome.wav")
            soundMap[nome] = soundPool.load(file.absolutePath, 1)
        }
        fun playSound(nome: String) {
            soundMap[nome]?.let { id ->
                soundPool.play(id, 1f, 1f, 1, 0, 1f)
            }
        }




        enableEdgeToEdge()
        setContent {
            viewModel= viewModel()

            viewModel.setRiproduzioneSuono(::playSound)
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