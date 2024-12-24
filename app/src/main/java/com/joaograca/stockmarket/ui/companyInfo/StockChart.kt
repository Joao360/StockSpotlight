package com.joaograca.stockmarket.ui.companyInfo

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaograca.stockmarket.domain.model.IntradayInfo
import com.joaograca.stockmarket.ui.theme.StockSpotlightTheme
import java.time.LocalDateTime
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StockChart(
    modifier: Modifier = Modifier,
    infos: List<IntradayInfo> = emptyList(),
    graphColor: Color = Color.Green,
) {
    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0
    }

    val lowerValue = remember(infos) {
        infos.minOfOrNull { it.close }?.toInt() ?: 0
    }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / infos.size
        (0 until infos.size -1  step 2).forEach { i ->
            val info = infos[i]
            val hour = info.date.hour

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    /* text = */ hour.toString(),
                    /* x = */ spacing + i * spacePerHour,
                    /* y = */ size. height - 5,
                    /* paint = */ textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f
        (1..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    /* text = */ round(lowerValue + priceStep * i).toString(),
                    /* x = */ 30f,
                    /* y = */ size.height - spacing - i * size.height / 5f,
                    /* paint = */ textPaint
                )
            }
        }

        var lastX = 0f
        val strokePath = Path().apply {
            val height = size.height
            for (i in infos.indices) {
                val info = infos[i]
                val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - leftRatio.toFloat() * height
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - rightRatio.toFloat() * height
                if (i == 0) {
                    moveTo(x = x1, y = y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(
                    /* x1 = */ x1,
                    /* y1 = */ y1,
                    /* x2 = */ lastX,
                    /* y2 = */ (y1 + y2) / 2f
                )
            }
        }
        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview
@Composable
private fun PreviewStockChart() {
    StockSpotlightTheme {
        StockChart(
            modifier = Modifier.fillMaxSize(),
            infos = listOf(
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T09:00:00"),
                    close = 100.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T10:00:00"),
                    close = 110.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T11:00:00"),
                    close = 90.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T12:00:00"),
                    close = 130.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T13:00:00"),
                    close = 100.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T14:00:00"),
                    close = 99.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T15:00:00"),
                    close = 101.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T16:00:00"),
                    close = 105.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T17:00:00"),
                    close = 102.0
                ),
                IntradayInfo(
                    date = LocalDateTime.parse("2021-01-01T18:00:00"),
                    close = 110.0
                ),
            )
        )
    }
}