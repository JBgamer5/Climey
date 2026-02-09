package com.alejandro.climey.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getCurrentLangCode(): String {
    return Locale.getDefault().language
}

fun String.toLocalDate(): LocalDate {
    return try {
        LocalDate.parse(this)
    } catch (_: Exception) {
        LocalDate.now()
    }
}

fun String.toLocalDateTime(): LocalDateTime {
    return try {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        LocalDateTime.parse(this, format)
    } catch (_: Exception) {
        LocalDateTime.now()
    }
}

fun String.replaceFirstUpperCase(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}