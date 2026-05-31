package com.example.simon_consegna_intermedia.ui.DettaglioPartitaActyvity

import PartitaObject

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.simon_consegna_intermedia.ui.components.ScreenDettaglioPartita
import com.example.simon_consegna_intermedia.ui.theme.Simon_Consegna_IntermediaTheme

class DettaglioPartitaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var partitaString = getIntent().getStringExtra("partita")
        if(partitaString==null) partitaString="Errore"
        val errorIndex = getIntent().getIntExtra("errorIndex",-1)
        val partita=PartitaObject(partitaString,errorIndex)

        enableEdgeToEdge()
        setContent {

            Simon_Consegna_IntermediaTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    ScreenDettaglioPartita(Modifier.Companion.padding(innerPadding),partita)()

                }
            }
        }
    }
}