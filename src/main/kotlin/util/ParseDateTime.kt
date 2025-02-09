package util

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun parseDateTime(dateStr: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(
        "EEE, dd MMM yyyy HH:mm:ss z",
        Locale.ENGLISH
    )

    return ZonedDateTime.parse(dateStr, formatter)
        .toLocalDateTime()
}