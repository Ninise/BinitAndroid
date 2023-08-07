package com.ndteam.wasteandroidapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

object ViewUtils {

    @Composable
    fun parseString(input: String): AnnotatedString {
        val builder = AnnotatedString.Builder()

        val italicRegex = Regex("\\\\(.*?)\\\\")
        val boldRegex = Regex("\\*\\*(.*?)\\*\\*")
        val mixedRegex = Regex("=(.*?)=")

        var startIndex = 0

        while (true) {
            val italicMatch = italicRegex.find(input, startIndex)
            val boldMatch = boldRegex.find(input, startIndex)
            val mixedMatch = mixedRegex.find(input, startIndex)

            val nextMatch = listOfNotNull(italicMatch, boldMatch, mixedMatch).minByOrNull { it.range.first }

            if (nextMatch == null) {
                builder.append(input.substring(startIndex, input.length))
                break
            }

            builder.append(input.substring(startIndex, nextMatch.range.first))

            val style = when (nextMatch.groupValues[0][0]) {
                '=' -> SpanStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
                '\\' -> SpanStyle(fontStyle = FontStyle.Italic)
                '*' -> SpanStyle(fontWeight = FontWeight.Bold)
                else -> SpanStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
            }

            builder.pushStyle(style)
            builder.append(nextMatch.groupValues[1])
            builder.pop()

            startIndex = nextMatch.range.last + 1
        }

        return builder.toAnnotatedString()
    }


}