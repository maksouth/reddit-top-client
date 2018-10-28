package name.mharbovskyi.redditsimpleclient.data

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())

internal fun LocalDateTime.toSeconds(): Long =
        atZone(ZoneId.systemDefault()).toInstant().epochSecond