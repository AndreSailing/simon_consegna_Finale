package com.example.simon_consegna_intermedia.ui.components

import PartitaObject
import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlin.text.iterator

class ScreenDettaglioPartita(
    private val modifier: Modifier= Modifier,
    private val partita: PartitaObject
    ) {
    @Composable
    operator fun invoke(){


        ConstraintLayout(modifier=modifier.fillMaxSize()) {
            val (t1,t2)=createRefs()
            var textPartita by remember { mutableStateOf(partita.partita) }
            var errorIndex by remember { mutableStateOf(partita.indiceErrore) }
            val scroll = rememberScrollState()
            Text(
                text="Partita",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .constrainAs(t1) {
                        top.linkTo(parent.top, margin = 80.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    ,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                buildAnnotatedString {
                    var j=0.0
                    for (l in textPartita){
                        if (j<errorIndex){
                            withStyle(style = SpanStyle(color = Color.Green)) {
                                append(l)
                            }
                        }else{
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append(l)
                            }
                        }
                        j+=0.5
                    }
                },
                modifier = modifier
                    .background(Color(0x22000000)) // grigio trasparente
                    .fillMaxWidth()
                    .padding(18.dp)
                    .heightIn(min = 60.dp, max = 60.dp)   // spazio fisso ≈ 3 righe
                    .verticalScroll(scroll)
                    .constrainAs(t2) {
                        top.linkTo(t1.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        }



    }
}