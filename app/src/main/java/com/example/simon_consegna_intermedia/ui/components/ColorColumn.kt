package com.example.simon_consegna_intermedia.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.simon_consegna_intermedia.ui.functions.GameViewModel
import com.example.simon_consegna_intermedia.ui.theme.colorMap

@Composable
fun ColorColumn(
    startIndex: Int,
    colorNames: List<String>,
    screenHeight: Dp,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
    onClick: (String) -> Unit
) {
    Column(modifier = modifier) {
        for (i in startIndex until colorNames.size step 2) {

            val color = colorMap[colorNames[i]] ?: Color.Black
            val alpha by animateFloatAsState(
                targetValue = if (viewModel.coloreAttivo.equals( colorNames[i])) 4f else 0.3f,
                animationSpec = tween(200)
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                    contentColor = Color.White
                ),
                onClick = { onClick(colorNames[i]) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / colorNames.size)
                    .alpha(alpha)
            ) {}

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
