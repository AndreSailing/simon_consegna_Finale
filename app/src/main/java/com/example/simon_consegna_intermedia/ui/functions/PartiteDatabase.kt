package com.example.simon_consegna_intermedia.ui.functions

import PartitaObject
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

    fun insertPartita(partita: String, errore: Int) {
        val db = writableDatabase
        val stmt = db.compileStatement(
            "INSERT INTO partite(partita, errore) VALUES(?, ?)"
        )
        stmt.bindString(1, partita)
        stmt.bindLong(2, errore.toLong())
        stmt.executeInsert()
    }

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