package com.example.simon_consegna_intermedia.ui.functions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    var errorPosition by mutableStateOf(0)
    var partitaDaMostrare by mutableStateOf("")
        private set
    var inputInseritoB by mutableStateOf(false)
        private set
    var inputInseritoS by mutableStateOf("")
        private set

    var sequenza by mutableStateOf(listOf<String>())
        private set

    var coloreAttivo by mutableStateOf<String?>(null)
        private set
    var gamePaused by mutableStateOf(false)
        private set
    var gameEnded by mutableStateOf(false)
        private set
    var mostroSequenza by mutableStateOf(false)
        private set

    fun startGame() {

        viewModelScope.launch {
            checkStateToStart()
            sequenza = listOf()
            aggiungiStep()

            while (true) {
                mostraSequenza()
                val ok = attendiInputGiocatore()
                if (!ok) break
                delay(100)
                partitaDaMostrare=""
                aggiungiStep()
            }

            gamePaused = true
            gameEnded = true
        }
    }

    private fun aggiungiStep() {
        sequenza = sequenza + colorGeneration()
    }

    private suspend fun mostraSequenza() {
        mostroSequenza=true
        delay(500)
        for (c in sequenza) {

            while (gamePaused) delay(50)

            coloreAttivo = c
            delay(600)

            while (gamePaused) delay(50)

            coloreAttivo = null
            delay(200)
        }
        mostroSequenza=false
    }

    fun stateSwitch() {
        if(!gameEnded) gamePaused = !gamePaused
    }

    private suspend fun attendiInputGiocatore(): Boolean {
        for (color in sequenza) {
            if (!controllaColore(color)) return false
            errorPosition++
        }
        errorPosition=0
        return true
    }

    private suspend fun controllaColore(color: String): Boolean {


        // Aspetta input del giocatore
        while (!inputInseritoB) {
            delay(10)
        }

        inputInseritoB = false

        // illumina il colore premuto
        coloreAttivo = inputInseritoS
        partitaDaMostrare+=","+inputInseritoS
        delay(200)
        coloreAttivo = null
        return inputInseritoS ==color
    }

    fun inserisciColore(colore: String) {
        if(!(gamePaused||mostroSequenza)) {
            inputInseritoS = colore
            inputInseritoB = true
        }
    }
    fun getPartita(): String{
        var sReturn=""
        for (s in sequenza){
            sReturn+=","+s
        }
        return sReturn.drop(1)
    }
    fun getTextPause(pause: String,resume: String): String{
        if (gamePaused) return pause
        return resume
    }
    private suspend fun checkStateToStart(){
        while (gameEnded||gamePaused) delay(50)
    }
    fun getErrorIndex(): Int {
        if (gameEnded) return errorPosition
        return sequenza.size
    }

}
