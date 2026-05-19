package com.example.simon_consegna_intermedia.ui.functions

import com.example.simon_consegna_intermedia.ui.theme.colorMap

fun colorGeneration(partita: String): String {
    val colorNames = colorMap.keys.toList()
    val randomColor=colorNames.random()
    return partita+","+randomColor

}