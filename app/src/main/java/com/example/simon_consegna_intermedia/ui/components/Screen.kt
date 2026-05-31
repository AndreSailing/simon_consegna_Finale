package com.example.simon_consegna_intermedia.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.simon_consegna_intermedia.R
import com.example.simon_consegna_intermedia.ui.functions.GameViewModel


class Screen(
    private val modifier: Modifier= Modifier,
    private val viewModel: GameViewModel,
    private val onClickNewActivity:(String)->Unit

) {
    @Composable
    operator fun invoke(){



        val configuration= LocalConfiguration.current
        when(configuration.orientation){
            Configuration.ORIENTATION_PORTRAIT->{
                OrientationPortrait()
            }

            Configuration.ORIENTATION_LANDSCAPE->{
                OrientationLandscape()
            }
            else -> OrientationLandscape()
        }
    }
    fun checkButton(funzione:()-> Unit){
        if (!(viewModel.gameEnded||viewModel.gamePaused)) funzione()
    }
    @Composable
    private fun OrientationPortrait(){

        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        ConstraintLayout(modifier = modifier.fillMaxWidth()) {
            val (columnMatrix, textPartita,buttons)=createRefs()


            ColumnMatrix(
                viewModel,
                screenHeight = screenHeight*0.8f,
                modifier= Modifier.constrainAs(columnMatrix){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                onClick = { color ->
                    viewModel.inserisciColore(color)
                    }
            )()
            GameText(viewModel,  Modifier.constrainAs(textPartita){
                top.linkTo(columnMatrix.bottom)
            })

            GameButton({viewModel.startGame() },{

                onClickNewActivity(viewModel.getPartita())
            },viewModel, modifier= Modifier.constrainAs(buttons){
                top.linkTo(textPartita.bottom)
            })()

        }

    }
    @Composable
    private fun OrientationLandscape(){
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        ConstraintLayout(modifier = modifier.fillMaxWidth()) {
            val (columnMatrix, column2)=createRefs()

            Column(Modifier.constrainAs(columnMatrix){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }.fillMaxWidth(0.5f)) {
                ColumnMatrix(
                    viewModel,
                    screenHeight = screenHeight,
                    modifier= Modifier,
                    onClick = { color ->
                        viewModel.inserisciColore(color)
                        }
                )()
            }
            Column(Modifier.constrainAs(column2){
                top.linkTo(parent.top)
                start.linkTo(columnMatrix.end)
            }.fillMaxWidth(0.5f)) {
            GameText(viewModel,  Modifier)

            GameButton({viewModel.startGame()},{

                onClickNewActivity(viewModel.getPartita())
            },viewModel, modifier= Modifier)()
            }

        }
    }
}