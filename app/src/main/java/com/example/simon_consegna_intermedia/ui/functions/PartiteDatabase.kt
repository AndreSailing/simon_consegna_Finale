package com.example.simon_consegna_intermedia.ui.functions

import PartitaObject
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Gestisce la creazione e l'accesso al database SQLite contenente le partite giocate.
 *
 * Il database include una singola tabella:
 *  - partite(id INTEGER PRIMARY KEY AUTOINCREMENT,
 *            partita TEXT NOT NULL,
 *            errore INTEGER NOT NULL)
 *
 * La classe fornisce metodi per inserire una partita e recuperare tutte le partite salvate.
 *
 * @constructor Crea un'istanza del database helper con nome "partite.db".
 */
class PartiteDatabase(context: Context) :
    SQLiteOpenHelper(context, "partite.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE partite(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                partita TEXT NOT NULL,
                errore INTEGER NOT NULL
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS partite")
        onCreate(db)
    }

    /**
     * Inserisce una nuova partita nel database.
     *
     * @param partita Stringa che rappresenta la sequenza giocata.
     * @param errore Indice dell'errore commesso dal giocatore.
     *
     * Utilizza una prepared statement per evitare SQL injection
     * e migliorare le prestazioni.
     */
    fun insertPartita(partita: String, errore: Int) {
        val db = writableDatabase
        val stmt = db.compileStatement(
            "INSERT INTO partite(partita, errore) VALUES(?, ?)"
        )
        stmt.bindString(1, partita)
        stmt.bindLong(2, errore.toLong())
        stmt.executeInsert()
    }

    /**
     * Recupera tutte le partite salvate nel database.
     *
     * @return Lista di oggetti PartitaObject contenenti:
     *  - partita: sequenza giocata
     *  - errore: indice dell'errore
     *
     * Il cursore viene chiuso automaticamente al termine della lettura.
     */
    fun getAllPartite(): List<PartitaObject> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT partita, errore FROM partite", null)

        val lista = mutableListOf<PartitaObject>()

        while (cursor.moveToNext()) {
            val partita = cursor.getString(0)
            val errore = cursor.getInt(1)
            lista.add(PartitaObject(partita, errore))
        }

        cursor.close()
        return lista
    }

}