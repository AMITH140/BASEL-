package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.domain.SleepStage
import com.example.domain.SleepStageData
import com.example.ui.theme.BaselSurfaceVariant
import com.example.ui.theme.SleepAwake
import com.example.ui.theme.SleepDeep
import com.example.ui.theme.SleepLight
import com.example.ui.theme.SleepREM
import java.time.temporal.ChronoUnit

@Composable
fun Hypnogram(stages: List<SleepStageData>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BaselSurfaceVariant)
            .padding(16.dp)
    ) {
        if (stages.isEmpty()) return

        val firstTime = stages.first().startTime
        val lastTime = stages.last().endTime
        val totalDurationMillis = ChronoUnit.MILLIS.between(firstTime, lastTime).toFloat()

        if (totalDurationMillis <= 0) return

        Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            val width = size.width
            val height = size.height

            val getYForStage = { stage: SleepStage ->
                when (stage) {
                    SleepStage.AWAKE -> 0f
                    SleepStage.REM -> height * 0.33f
                    SleepStage.LIGHT -> height * 0.66f
                    SleepStage.DEEP -> height
                    else -> height * 0.66f
                }
            }

            val getColorForStage = { stage: SleepStage ->
                when (stage) {
                    SleepStage.AWAKE -> SleepAwake
                    SleepStage.REM -> SleepREM
                    SleepStage.LIGHT -> SleepLight
                    SleepStage.DEEP -> SleepDeep
                    else -> Color.Gray
                }
            }

            val path = Path()
            var isFirst = true

            for (stageData in stages) {
                val startX = (ChronoUnit.MILLIS.between(firstTime, stageData.startTime).toFloat() / totalDurationMillis) * width
                val endX = (ChronoUnit.MILLIS.between(firstTime, stageData.endTime).toFloat() / totalDurationMillis) * width
                val y = getYForStage(stageData.stage)

                if (isFirst) {
                    path.moveTo(startX, y)
                    isFirst = false
                } else {
                    // Vertical line to new stage
                    path.lineTo(startX, y)
                }
                
                // Horizontal line for duration
                path.lineTo(endX, y)
                
                // Draw filled rectangle for this stage to highlight the color underneath the line
                drawRect(
                    color = getColorForStage(stageData.stage).copy(alpha = 0.8f),
                    topLeft = Offset(startX, y - 4.dp.toPx()),
                    size = androidx.compose.ui.geometry.Size(endX - startX, 8.dp.toPx())
                )
            }

            drawPath(
                path = path,
                color = Color.White,
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}
