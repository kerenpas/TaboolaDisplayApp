package com.example.tabooladisplayapp.shimmer

import android.view.View
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp

@Composable
private fun ShimmerEffect() {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 200, translateAnim - 200),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerList(brush = brush)
}

@Composable
private fun ShimmerList(brush: Brush) {
    Column(modifier = Modifier.fillMaxSize()) {
        repeat(5) {
            Spacer(modifier = Modifier.height(8.dp))
            ShimmerItem(brush = brush)
        }
    }
}

@Composable
private fun ShimmerItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Thumbnail
        Spacer(
            modifier = Modifier
                .size(80.dp)
                .background(brush)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            // Title
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Description
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(16.dp)
                    .background(brush)
            )
        }
    }
}

object ShimmerBridge {
    @JvmStatic
    fun mount(composeView: ComposeView) {
        composeView.setContent {
            ShimmerEffect()
        }
    }
}
