package self.adragon.aviaroute_web.data.model.converters

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object LocalDateConverter {
    fun fromEpochSecondsStringDate(epochSeconds: Long?, pattern: String? = null): String =
        fromEpochSecondsStringDateTime(epochSeconds, pattern).split(',').first()

    private fun fromEpochSecondsStringDateTime(
        epochSeconds: Long?,
        defaultPattern: String? = null
    ): String {
        if (epochSeconds == null) return ""

        val pattern = defaultPattern ?: "dd MMM yyyy, HH:mm"
        val formatter = DateTimeFormatter.ofPattern(pattern)

        val instant = Instant.ofEpochSecond(epochSeconds)
        val localDateTime = instant.atZone(ZoneId.of("Europe/Moscow")).toLocalDateTime()

        return localDateTime.format(formatter)
    }

    fun fromEpochSecondToTimeString(epochSeconds: Long?): String {
        if (epochSeconds == null) return ""

        val secondsInMinute = 60
        val secondsInHour = secondsInMinute * 60
        val secondsInDay = secondsInHour * 24

        val hourInDay = 24
        val minuteInHour = 60

        val days = epochSeconds.div(secondsInDay)
        val hours = epochSeconds.div(secondsInHour) % hourInDay
        val minutes = epochSeconds.div(secondsInMinute) % minuteInHour
        val seconds = epochSeconds % secondsInMinute

        val dayStr = if (days > 0) "$days д., " else ""
        val hoursStr = if (hours > 0) "$hours ч., " else ""
        val minutesStr = if (minutes > 0) "$minutes м., " else ""
        val secondsStr = if (seconds > 0) "$seconds с." else ""

        return dayStr + hoursStr + minutesStr + secondsStr
    }
}
