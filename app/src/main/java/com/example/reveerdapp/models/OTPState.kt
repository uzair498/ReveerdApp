package com.example.reveerdapp.models

data class OTPState(
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null
)
