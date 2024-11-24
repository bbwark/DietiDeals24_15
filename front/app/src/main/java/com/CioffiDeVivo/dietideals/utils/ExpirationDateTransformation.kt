package com.CioffiDeVivo.dietideals.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ExpirationDateTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmedText = text.text.take(4)
        var out = ""
        for(idx in trimmedText.indices){
            if(idx == 2){
                out += "/"
            }
            out += trimmedText[idx]
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when{
                    offset <= 2 -> offset
                    offset <= 4 -> offset + 1
                    else -> 5
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    else -> 4
                }
            }
        }
        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}