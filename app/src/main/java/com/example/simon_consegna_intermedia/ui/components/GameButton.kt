package com.example.simon_consegna_intermedia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simon_consegna_intermedia.R
import com.example.simon_consegna_intermedia.ui.functions.GameViewModel

class GameButton (
    private val onClick1: () -> Unit,
    private val onClick2: () -> Unit,
    private val viewModel: GameViewModel,
    private val modifier: Modifier
){


    @Composable
    operator fun invoke() {
            val textPause=stringResource(R.string.b3Text)
            val textResume=stringResource(R.string.b3Text2)


            LazyColumn(modifier= modifier){
                    item {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {onClick1()},
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.b1Text))
                        }

                        Button(
                            onClick ={viewModel.stateSwitch()},
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(viewModel.getTextPause(textPause,textResume))
                        }
                    }
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                onClick2()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.b2Text))
                        }
                    }
                }
            }
    }

}




