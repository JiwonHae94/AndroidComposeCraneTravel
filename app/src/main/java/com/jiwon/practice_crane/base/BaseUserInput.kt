package com.jiwon.practice_crane.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jiwon.practice_crane.ui.captionTextStyle

@Composable
fun SinmpleUserInput(
    text : String? = null,
    caption : String? = null,
    @DrawableRes vectorImageId : Int? = null
){

}

@Composable
fun CraneUserInput(
    text : String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {},
    caption : String? = null,
    @DrawableRes vectorImageId : Int? = null,
    tint : Color = LocalContentColor.current
){

}

@ExperimentalMaterialApi
@Composable
fun CraneEditableUserInput(
    hint : String,
    caption : String? = null,
    @DrawableRes vectorImageId:Int? = null,
    onInputChanged: (String) -> Unit
){
    var textFieldState by remember { mutableStateOf(TextFieldValue(text = hint)) }
    val isHint = {textFieldState.text == hint }

    CraneBaseInput(
        caption = caption,
        tintIcon = { !isHint() },
        showCaption = { !isHint() },
        vectorImageId = vectorImageId
    ){
        BasicTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                if (!isHint()) onInputChanged(textFieldState.text)
            },
            textStyle = if (isHint()) {
                captionTextStyle.copy(color = LocalContentColor.current)
            } else {
                MaterialTheme.typography.body1.copy(color = LocalContentColor.current)
            },
            cursorBrush = SolidColor(LocalContentColor.current)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun CraneBaseInput(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    caption:String ? = null,
    @DrawableRes vectorImageId : Int? = null,
    showCaption: () -> Boolean = { true },
    tintIcon : () -> Boolean,
    tint : Color = LocalContentColor.current,
    content : @Composable () -> Unit
){
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = MaterialTheme.colors.primaryVariant
    ){
        Row(Modifier.padding(all = 12.dp)){
            if(vectorImageId != null){
                Icon(
                    modifier = Modifier.size(24.dp, 24.dp),
                    painter = painterResource(id = vectorImageId),
                    tint = if(tintIcon()) tint else Color(0x80FFFFFF),
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))

                if(caption != null && showCaption()){
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = caption,
                        style = (captionTextStyle).copy(color = tint)
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Row(
                    Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ){
                    content
                }
            }
        }
    }

}