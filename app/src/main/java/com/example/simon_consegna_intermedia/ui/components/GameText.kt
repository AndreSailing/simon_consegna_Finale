package com.example.simon_consegna_intermedia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simon_consegna_intermedia.ui.functions.GameViewModel

@Composable
fun GameText(viewModel: GameViewModel, modifier:  androidx.compose.ui.Modifier){

    val scroll = rememberScrollState()
    Text(
        buildAnnotatedString {
            if (!viewModel.gameEnded){
                withStyle(style = SpanStyle(color = Color.Green)) {
                    append(viewModel.partitaDaMostrare.drop(1))
                }
            }else{
                withStyle(style = SpanStyle(color = Color.Green)) {
                    append(viewModel.partitaDaMostrare.drop(1).dropLast(1))
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append(viewModel.partitaDaMostrare.last())
                }
            }
        },
        modifier = modifier
            .background(Color(0x22000000)) // grigio trasparente
            .fillMaxWidth()
            .padding(18.dp)
            .heightIn(min = 60.dp, max = 60.dp)   // spazio fisso ≈ 3 righe
            .verticalScroll(scroll),
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic

    )

}