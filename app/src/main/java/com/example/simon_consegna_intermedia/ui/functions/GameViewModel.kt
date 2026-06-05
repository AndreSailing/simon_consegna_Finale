package com.example.simon_consegna_intermedia.ui.functions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
/**
 * ViewModel principale che gestisce tutta la logica del gioco Simon.
 *
 * Responsabilità:
 * - Generazione della sequenza casuale di colori
 * - Riproduzione della sequenza al giocatore
 * - Gestione dell'input del giocatore e verifica della correttezza
 * - Gestione degli stati del gioco (pausa, fine, riproduzione sequenza)
 * - Comunicazione con la UI tramite variabili Compose osservabili
 *
 * Il ViewModel utilizza coroutine tramite viewModelScope per gestire
 * ritardi, animazioni e flussi di gioco senza bloccare il thread principale.
 */
class GameViewModel : ViewModel() {
    /** Funzione per riprodurre un suono associato a un colore. */
    private var playSound:(String)-> Unit={}

    var errorPosition by mutableStateOf(0)
    /** Stringa che rappresenta la partita mostrata nella schermata di gioco. */
    var partitaDaMostrare by mutableStateOf("")
        private set
    /** Flag che indica se il giocatore ha inserito un input. */
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
    /**
    * Avvia una nuova partita.
    *
    * Flusso:
    * 1. Attende che il gioco sia in uno stato valido per iniziare
    * 2. Reset della sequenza
    * 3. Aggiunge un primo step
    * 4. Mostra la sequenza
    * 5. Attende input del giocatore e verifica
    * 6. In caso di errore → fine partita
    * 7. In caso di correttezza → aggiunge un nuovo step e continua
    */
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
            delay(200)
            playSound("E")
        }
    }

    private fun aggiungiStep() {
        sequenza = sequenza + colorGeneration()
    }
    /**
     * Mostra la sequenza al giocatore illuminando i colori uno alla volta e riproducendo il suono.
     * Rispetta eventuali pause del giocatore.
     */
    private suspend fun mostraSequenza() {
        mostroSequenza=true
        delay(500)
        for (c in sequenza) {

            while (gamePaused) delay(50)

            coloreAttivo = c
            playSound(c)

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
    /**
     * Attende l'input del giocatore e verifica ogni colore della sequenza.
     *
     * @return true se il giocatore ha inserito correttamente tutta la sequenza,
     *         false in caso di errore.
     */
    private suspend fun attendiInputGiocatore(): Boolean {
        for (color in sequenza) {
            if (!controllaColore(color)) return false
            errorPosition++
        }
        errorPosition=0
        return true
    }
    /**
     * Controlla se il colore inserito dal giocatore corrisponde a quello atteso.
     *
     * @param color colore corretto da confrontare
     * @return true se l'input è corretto, false altrimenti
     */
    private suspend fun controllaColore(color: String): Boolean {


        // Aspetta input del giocatore
        while (!inputInseritoB) {
            delay(10)
        }

        inputInseritoB = false

        // illumina il colore premuto
        coloreAttivo = inputInseritoS
        playSound(inputInseritoS)
        partitaDaMostrare+=","+inputInseritoS
        delay(200)
        coloreAttivo = null
        return inputInseritoS ==color
    }
    /**
     * Registra un colore inserito dal giocatore.
     * Ignora input se il gioco è in pausa o sta mostrando la sequenza.
     */
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
    /**
     * Attende che il gioco non sia né in pausa né terminato prima di iniziare.
     */
    private suspend fun checkStateToStart(){
        while (gameEnded||gamePaused) delay(50)
    }
    fun getErrorIndex(): Int {
        if (gameEnded) return errorPosition
        return sequenza.size
    }
    fun setRiproduzioneSuono(f:(String)-> Unit){
        playSound=f
    }
}
