package com.example.simon_consegna_intermedia.ui.components

import PartitaObject
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp




@Composable
fun GameList(modifier: Modifier,partite: List<PartitaObject>,onClickText:(PartitaObject)-> Unit){
    ConstraintLayout(modifier = modifier.fillMaxWidth()){
        val (col1)=createRefs()
        LazyColumn(modifier= Modifier.constrainAs(col1){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            }) { items(partite.size) {i->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val quadratiPremuti = (partite[i].partita.length + 1) / 2
                    Text(
                        text = quadratiPremuti.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        buildAnnotatedString {
                            var j=0.0
                            for (l in partite[i].partita){
                                if (j<partite[i].indiceErrore){
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f).clickable {
                            onClickText(partite[i])
                        }
                    )


                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = DividerDefaults.Thickness,
                    color = MaterialTheme.colorScheme.outlineVariant
                )


            }

            //necessario per rendere ben visibile l'ultimo elemento della lista
            item { Spacer(modifier = Modifier.height(150.dp)) }
        }

    }
}