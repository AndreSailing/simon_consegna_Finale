package com.example.simon_consegna_intermedia.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.simon_consegna_intermedia.ui.components.GameList
import com.example.simon_consegna_intermedia.ui.theme.Simon_Consegna_IntermediaTheme
import com.example.simon_consegna_intermedia.R
import com.example.simon_consegna_intermedia.ui.SecondActivity.GameActivity


class MainActivity : ComponentActivity() {

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val partita = result.data?.getStringExtra("partita")
                if (partita != null) {
                    viewModel.addPartita(partita)
                }
            }
        }

        setContent {
            Simon_Consegna_IntermediaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            launcher.launch(Intent(this, GameActivity::class.java))
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Aggiungi")
                        }
                    }
                ) { innerPadding ->
                    SecondScreen(
                        Modifier.padding(innerPadding),
                        viewModel.partite
                    )
                }
            }
        }
    }
}

@Composable
fun SecondScreen(modifier: Modifier,partite: List<String>){

    ConstraintLayout(modifier=modifier.fillMaxWidth()
    ) {
        val (gameText,title,spacer1,spacer2)=createRefs()
        Spacer(modifier= Modifier.constrainAs(spacer1){
            top.linkTo(parent.top)

        }.height(16.dp))
        Text(
            modifier= Modifier.constrainAs(title){
                top.linkTo(spacer1.bottom)
            }.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            text= stringResource(R.string.title)

        )
        Spacer(modifier= Modifier.constrainAs(spacer2){
            top.linkTo(title.bottom)

        }.height(16.dp))
        GameList( Modifier.constrainAs(gameText){
            top.linkTo(spacer2.bottom)
            start.linkTo(parent.start)
        },partite)

    }


}