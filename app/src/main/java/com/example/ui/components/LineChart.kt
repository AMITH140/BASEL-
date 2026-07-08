package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BaselSurfaceVariant
import com.example.ui.theme.BaselDivider

@Composable
fun LineChart(
    data: List<Float>,
    lineColor: Color,
    modifier: Modifier = Modifier,
    minValue: Float = 0f,
    maxValue: Float = 100f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BaselSurfaceVariant)
            .padding(16.dp)
    ) {
        if (data.isEmpty()) return

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val stepX = if (data.size > 1) width / (data.size - 1) else width
            
            // Draw grid lines
            val gridLines = 4
            for (i in 0..gridLines) {
                val y = height * (i.toFloat() / gridLines)
                drawLine(
                    color = BaselDivider,
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            val path = Path()
            val range = maxValue - minValue

            data.forEachIndexed { index, value ->
                val x = index * stepX
                val normalizedValue = ((value - minValue) / range).coerceIn(0f, 1f)
                val y = height - (normalizedValue * height)

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }

            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
            
            // Draw data points
            data.forEachIndexed { index, value ->
                val x = index * stepX
                val normalizedValue = ((value - minValue) / range).coerceIn(0f, 1f)
                val y = height - (normalizedValue * height)
                
                drawCircle(
                    color = BaselSurfaceVariant,
                    radius = 4.dp.toPx(),
                    center = Offset(x, y)
                )
                drawCircle(
                    color = lineColor,
                    radius = 3.dp.toPx(),
                    center = Offset(x, y),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}
