package com.CioffiDeVivo.dietideals.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import kotlin.math.max

class BidInputVisualTransformation(private val numberOfDecimals: Int = 2): VisualTransformation {

    private val symbols = DecimalFormat().decimalFormatSymbols

    override fun filter(text: AnnotatedString): TransformedText {
        val pointForThousands = symbols.groupingSeparator
        val commaForDecimal = symbols.decimalSeparator
        val zero = symbols.zeroDigit

        val inputText = text.text

        val integerPart = inputText
            .dropLast(numberOfDecimals)
            .reversed()
            .chunked(3)
            .joinToString(pointForThousands.toString())
            .reversed()
            .ifEmpty { zero.toString() }

        val decimalPart = inputText.takeLast(numberOfDecimals).let {
            if (it.length != numberOfDecimals) {
                List(numberOfDecimals - it.length) {
                    zero
                }.joinToString("") + it
            } else {
                it
            }
        }

        val formattedText = integerPart + commaForDecimal + decimalPart

        val newText = AnnotatedString(
            text = formattedText,
            spanStyles = text.spanStyles,
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = MovableCursorOffsetMapping(
            unmaskedText = text.toString(),
            maskedText = newText.toString(),
            numberOfDecimals = numberOfDecimals
        )
        return TransformedText(newText, offsetMapping)
    }

    private class MovableCursorOffsetMapping(private val unmaskedText: String, private val maskedText: String, private val numberOfDecimals: Int): OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when {
                unmaskedText.length <= numberOfDecimals -> {
                    maskedText.length - (unmaskedText.length - offset)
                }
                else -> {
                    offset + offsetMaskCount(offset, maskedText)
                }
            }

        override fun transformedToOriginal(offset: Int): Int =
            when {
                unmaskedText.length <= numberOfDecimals -> {
                    max(unmaskedText.length - (maskedText.length - offset), 0)
                }
                else -> {
                    offset - maskedText.take(offset).count { !it.isDigit() }
                }
            }

        private fun offsetMaskCount(offset: Int, maskedText: String): Int {
            var maskOffsetCount = 0
            var dataCount = 0
            for (maskChar in maskedText) {
                if (!maskChar.isDigit()) {
                    maskOffsetCount++
                } else if (++dataCount > offset) {
                    break
                }
            }
            return maskOffsetCount
        }
    }
}