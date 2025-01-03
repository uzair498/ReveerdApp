package com.example.reveerdapp.utils

import android.view.KeyEvent
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.BackspaceCommand
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.core.view.WindowCompat
import com.example.reveerdapp.ui.MyColors
import com.example.reveerdapp.ui.MyFonts

fun setStatusBarColor(isDark: Boolean, cuurentWindow: Window) {
    WindowCompat.getInsetsController(cuurentWindow, cuurentWindow.decorView).apply {
        isAppearanceLightStatusBars = isDark
    }
}

@Composable
fun OTPInputField(
    number: Int?,
    focusRequester: FocusRequester,
    onfocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember(number) { mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    index = if (number != null) 1 else 0
                )))
    }

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
//            .border(
//                width = 1.dp,
//                color = MyColors.locationButtonColor
//            )

            .background(color = MyColors.customOffWhite, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                val newNumber = newText.text
                if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                    onNumberChanged(newNumber.toIntOrNull())
                }
            },
            cursorBrush = SolidColor(MyColors.locationButtonColor),
            singleLine = true,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontFamily = MyFonts.CustomFonts,
                fontSize = 16.sp,
                color = MyColors.black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .padding(16.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onfocusChanged(it.isFocused)
                }
                .onKeyEvent { event ->
                    if (event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL && number == null) {
                       onKeyboardBack()
                    }
                    false
                },
            decorationBox = { innerBox ->
                innerBox()
                if (!isFocused && number == null)
                {
                    Text(
                        text = "-",
                        textAlign = TextAlign.Center,
                        color = MyColors.black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                        )
                }
            }

        )
    }

}
