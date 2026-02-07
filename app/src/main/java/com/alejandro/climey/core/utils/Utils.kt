package com.alejandro.climey.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
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
        LocalDateTime.parse(this)
    } catch (_: Exception) {
        LocalDateTime.now()
    }
}