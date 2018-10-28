package name.mharbovskyi.redditsimpleclient.presentation.model.mapper

import java.lang.StringBuilder
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

val imageFormats = listOf(".jpg", ".jpeg", ".jpg")
internal fun String.isImageFormat(): Boolean =
    imageFormats.any { this.endsWith(it) }

fun shortStringNumber(number: Int): String = with(StringBuilder()) {

    var double = number.toDouble()

    while (double > 1000) {
        double /= 1000
        append("K")
    }

    val decimalFormat = DecimalFormat("#.##")
    insert(0, decimalFormat.format(double))

    toString()
}

fun LocalDateTime.beforeNowString(): String {
    val period = Period.between(this.toLocalDate(), LocalDate.now())

    var string = ""

    if (period.years > 1) string = "${period.years} years ago"
    else if (period.years == 1) string =  "1 year ago"
    else if (period.months > 1) string =  "${period.months} months ago"
    else if (period.months == 1) string = "1 month ago"
    else if (period.days > 1) string =  "${period.days} ago"
    else if (period.days == 0) string =  "1 day ago"

    return string
}