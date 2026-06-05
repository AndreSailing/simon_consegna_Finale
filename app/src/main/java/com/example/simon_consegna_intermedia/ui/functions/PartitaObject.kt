/**
 * Rappresenta una singola partita del gioco Simon.
 *
 * Questa classe contiene i dati minimi necessari per descrivere
 * l’esito di una partita:
 *
 * @property partita Stringa che rappresenta la sequenza completa dei colori
 *                   generata dal gioco, separati da virgole.
 *
 * @property indiceErrore Indice dell’errore commesso dal giocatore.
 *                        - Se la partita è terminata per errore → indica la posizione
 *                          in cui il giocatore ha sbagliato.
 *                        - Se la partita è terminata con un "“Back di sistema" o premendo
 *                          il bottone "Fine Partita"-> indiceErrore=0
 *
 * Questa classe è utilizzata per:
 * - Passare i dati alla schermata di dettaglio (`ScreenDettaglioPartita`)
 * - Salvare o mostrare lo storico delle partite
 * - Evitare l’uso di liste parallele (sequenza + errori)
 */
data class PartitaObject(
    val partita: String,
    val indiceErrore: Int
)
