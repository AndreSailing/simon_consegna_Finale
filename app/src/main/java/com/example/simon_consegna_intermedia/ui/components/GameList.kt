package com.example.simon_consegna_intermedia.ui.components

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp




@Composable
fun GameList(modifier: Modifier,partite: List<String>){
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
                    val quadratiPremuti = (partite[i].length + 1) / 2
                    Text(
                        text = quadratiPremuti.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = partite[i],
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
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