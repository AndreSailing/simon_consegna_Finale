package com.example.simon_consegna_intermedia.ui.functions

import com.example.simon_consegna_intermedia.ui.theme.colorMap

fun colorGeneration(): String {
    val colorNames = colorMap.keys.toList()
    val randomColor=colorNames.random()
    return randomColor

}