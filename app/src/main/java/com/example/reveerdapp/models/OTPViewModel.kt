package com.example.reveerdapp.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val VALID_OTP_CODE = "123456"

class OTPViewModel : ViewModel() {
    private val _state = MutableStateFlow(OTPState())
    val state = _state.asStateFlow()

    fun onAction(action: OTPAction) {
        when (action) {
            is OTPAction.OnChangeFieldFocused -> {
                _state.update {
                    it.copy(
                        focusedIndex = action.index
                    )
                }
            }

            is OTPAction.OnEnterNumber -> {
                enterNumber(action.number, action.index)
            }

            OTPAction.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex)
                _state.update {
                    it.copy(
                        code = it.code.mapIndexed { index, number ->
                            if (index == previousIndex) {
                                null
                            } else {
                                number
                            }
                        },
                        focusedIndex = previousIndex
                    )
                }
            }
        }

    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode = state.value.code.mapIndexed { currentIndex, currentNumber ->
            if (currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        _state.update {
            it.copy(
                code = newCode,
                focusedIndex = if (wasNumberRemoved || it.code.getOrNull(index) != null) {
                    it.focusedIndex
                } else {
                    getNextFocusedIndex(
                        currentCode = it.code,
                        currentFocusedIndex = it.focusedIndex
                    )
                },
                isValid = null
//                isValid = if (newCode.none { it == null }) {
//                    newCode.joinToString("") == VALID_OTP_CODE
//                } else null
            )
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedIndex(currentCode: List<Int?>, currentFocusedIndex: Int?): Int? {
        if (currentFocusedIndex == null) {
            return null
        }
        if (currentFocusedIndex == 5) {
            return currentFocusedIndex
        }
        return getFirstEmptyFielIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFielIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if (index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if (number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }
}