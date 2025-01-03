package com.example.reveerdapp.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.reveerdapp.R


object MyColors {

    val white = Color(0xFFFFFFFF)
    val black = Color(0xFF000000)
    val customOffWhite = Color(0xFFB8B8B8)
    val dimwhite = Color(0xFFCFCFCF)
    val customBlue = Color(0xFF11368E)
    val locationButtonColor = Color(0xFF0939AC)
    val customSkyBlue = Color(0xFF1877F2)
    val customMsgColor = Color(0xFFA0A0A0)

}

object MyFonts {
    val CustomFonts = FontFamily(
        Font(R.font.poppins_regular),
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.SemiBold)
    )
}