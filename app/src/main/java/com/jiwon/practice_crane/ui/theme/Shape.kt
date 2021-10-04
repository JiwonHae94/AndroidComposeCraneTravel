package com.jiwon.practice_crane.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun Circle(color : Color){
    Canvas(Modifier.fillMaxSize()){
        drawCircle(color)
    }
}

@Composable
fun SemiRect(color : Color, lookingLeft: Boolean = true){
    val layoutDirection = LocalLayoutDirection.current

    Canvas(Modifier.fillMaxSize()){
        val offset = if (lookingLeft xor (layoutDirection == LayoutDirection.Rtl)) {
            Offset(0f, 0f)
        } else {
            Offset(size.width / 2, 0f)
        }
        val size = Size(width = size.width / 2, height = size.height)

        drawRect(size = size, topLeft = offset, color = color)
    }

}