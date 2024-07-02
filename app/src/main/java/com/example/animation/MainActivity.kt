package com.example.animation

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animation.ui.theme.AnimationTheme
import kotlinx.coroutines.delay
import java.util.Collections


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BorderAnimationLine()
                }
            }
        }
    }
}


@Composable
fun BorderAnimationLine(modifier: Modifier = Modifier) {
    val brush1 = Brush.sweepGradient(gradientColors)
    val brush2 = Brush.sweepGradient(gradientColors2)
    val brush3 = Brush.sweepGradient(gradientColors3)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 14, 19, 255))
    ) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f)) {
            Text(
                text = "Start playing some music",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Center),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
        StaggeredCircularLines(
            brush = brush1,
            durationMillis = 2000
        )
        StaggeredCircularLines(
            brush = brush1,
            durationMillis = 3000
        )
        StaggeredCircularLines(
            brush = brush2,
            durationMillis = 4000
        )
    }
}

val gradientColors = listOf(
    Color(91, 21, 50, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(91, 21, 50, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(91, 21, 50, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(91, 21, 50, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
)
val gradientColors2 = listOf(
    Color(67, 138, 145, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(67, 138, 145, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(67, 138, 145, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(67, 138, 145, 255),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
)
val gradientColors3 = listOf(
    Color.White,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.White,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.White,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.White,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
)

@Composable
fun CircularMovingLineCanvas(
    strokeWidth: Dp,
    durationMillis: Int,
    brush: Brush, modifier: Modifier = Modifier,

    ) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = EaseInSine),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val strokeWidthPx = strokeWidth.toPx()
        val width = size.width
        val height = size.height

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination (Rounded Rectangle)
            drawRoundRect(
                color = Color.Gray,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(width - strokeWidthPx, height - strokeWidthPx),
                style = Stroke(strokeWidthPx),
                cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx())
            )

            // Source (Rotating Circle with Rainbow Brush)
            rotate(angle) {
                drawCircle(
                    brush = brush,
                    radius = size.height,
                    blendMode = BlendMode.SrcIn
                )
            }

            restoreToCount(checkPoint)
        }
    }
}
//@Composable
//fun CircularMovingLineCanvas(
//    strokeWidth: Dp,
//    durationMillis: Int,
//    brush: Brush,
//    glowRadius: Dp = 10.dp, // Adjust glow radius as needed
//    glowColor: Color = Color.White, // Adjust glow color as needed
//    modifier: Modifier = Modifier
//) {
//    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
//    val angle by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 360f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(durationMillis, easing = EaseInSine),
//            repeatMode = RepeatMode.Restart
//        ),
//        label = "rotation"
//    )
//    Canvas(modifier = modifier.fillMaxSize()) {
//        val strokeWidthPx = strokeWidth.toPx()
//        val width = size.width
//        val height = size.height
//        val paint =
//            Paint().apply {
//                style = PaintingStyle.Stroke
//                this.strokeWidth = 20f
//            }
//
//        val frameworkPaint =
//            paint.asFrameworkPaint()
//
//        val color = Color.Transparent
//        this.drawIntoCanvas {
//            val transparent = color
//                .copy(alpha = 0f)
//                .toArgb()
//
//            frameworkPaint.color = transparent
//
//            frameworkPaint.setShadowLayer(
//                10f,
//                0f,
//                0f,
//                color
//                    .copy(alpha = .5f)
//                    .toArgb()
//            )
//            frameworkPaint.setShadowLayer(
//                20f,
//                0f,
//                0f,
//                color
//                    .copy(alpha = .5f)
//                    .toArgb()
//            )
//
//
//
//
//            drawRoundRect(
//                Color.Gray,
//                topLeft = Offset(2f, 2f),
//                size = Size(width, height),
//                cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
//                style = Stroke(width = 2.dp.toPx())
//            )
//        }
//        with(drawContext.canvas.nativeCanvas) {
//            val checkPoint = saveLayer(null, null)
//
//            // Destination (Rounded Rectangle)
//            drawRoundRect(
//                color = Color.Gray,
//                topLeft = Offset(strokeWidthPx /2, strokeWidthPx / 2),
//                size = Size(width - strokeWidthPx, height - strokeWidthPx),
//                style = Stroke(strokeWidthPx),
//                cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx())
//            )
//
//            // Source (Rotating Circle with Rainbow Brush)
//            rotate(angle) {
//                drawCircle(
//                    brush = brush,
//                    radius = size.height,
//                    blendMode = BlendMode.SrcIn
//                )
//            }
//
//            restoreToCount(checkPoint)
//        }
//
//
//    }
//        }


@Composable
fun StaggeredCircularLines(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1000,
    brush: Brush = Brush.sweepGradient(gradientColors),
    lineCount: Int = 2
) {
    val brush1 = Brush.sweepGradient(gradientColors)
    val brush2 = Brush.sweepGradient(gradientColors2)
    val numLines = lineCount // Number of lines to display
    val delayBetweenLines = 500L // Delay in milliseconds
    val visibleLines = remember { mutableStateListOf<Int>() }

    // Trigger the appearance of lines sequentially
    LaunchedEffect(key1 = Unit) {
        for (i in 0 until numLines) {
            delay(delayBetweenLines)
            visibleLines.add(i)
        }
    }

    // Display the lines based on visibility
    for (i in 0 until numLines) {
        if (i in visibleLines) {
//                val brush = if (i % 2 == 0) brush1 else brush2 // Alternate brushes
            CircularMovingLineCanvas(
                strokeWidth = (1.5).dp,
                durationMillis = (durationMillis), // Vary duration
                brush = brush
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimationTheme {
        BorderAnimationLine()
    }
}