package com.example.simon_consegna_intermedia.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.simon_consegna_intermedia.ui.functions.GameViewModel
import com.example.simon_consegna_intermedia.ui.theme.colorMap

class ColumnMatrix(
    private val viewModel: GameViewModel,
    private val screenHeight: Dp,
    private val modifier: Modifier = Modifier.Companion,
    private val onClick: (String) -> Unit
) {

    @Composable
    operator fun invoke() {
        val colorNames = colorMap.keys.toList()


        ConstraintLayout(modifier = modifier.fillMaxWidth()) {

            val (col1, col2) = createRefs()

            // Colonna sinistra (indici pari)
            ColorColumn(
                startIndex = 0,
                colorNames = colorNames,
                screenHeight = screenHeight,
                modifier = Modifier.Companion.constrainAs(col1) {
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(col2.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    width = Dimension.Companion.fillToConstraints
                },viewModel,
                onClick = onClick
            )

            // Colonna destra (indici dispari)
            ColorColumn(
                startIndex = 1,
                colorNames = colorNames,
                screenHeight = screenHeight,
                modifier = Modifier.Companion.constrainAs(col2) {
                    start.linkTo(col1.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(parent.top)
                    width = Dimension.Companion.fillToConstraints
                },viewModel,
                onClick = onClick
            )
        }
    }
}