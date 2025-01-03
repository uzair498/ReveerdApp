package com.example.reveerdapp.models

sealed interface OTPAction {
    data class OnEnterNumber(val number: Int?, val index: Int) : OTPAction
    data class OnChangeFieldFocused(val index: Int) : OTPAction
    data object OnKeyboardBack : OTPAction
}