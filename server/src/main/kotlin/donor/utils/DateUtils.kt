package donor.utils

import org.joda.time.format.ISODateTimeFormat
import java.time.Instant
import java.util.*


object DateUtils {

    private val format = ISODateTimeFormat.dateTime()

    fun toISOString(datetime: Instant): String {
        return format.print(datetime.toEpochMilli())
    }

    fun fromISOString(datetime: String): Instant {
        return format.parseDateTime(datetime).toDate().toInstant()
    }

    fun startTimeFromISOString(datetime: String, timezone: String): Instant {
        val calendar = createCalendarInstance(datetime, timezone)
        return calendar.time.toInstant()
    }

    fun endTimeFromISOString(datetime: String, timezone: String): Instant {
        val calendar = createCalendarInstance(datetime, timezone, 23, 59, 59)
        return calendar.time.toInstant()
    }

    fun regCloseTimeFromISOString(datetime: String, timezone: String): Instant {
        val calendar = createCalendarInstance(datetime, timezone, 22, 0, 0)
        return calendar.time.toInstant()
    }

    private fun createCalendarInstance(datetime: String, timezone: String,
                                       h: Int = 0, min: Int = 0, sec: Int = 0): Calendar {
        val calendar = Calendar.getInstance()
        val curTimezone = TimeZone.getTimeZone(timezone)
        calendar.timeZone = curTimezone
        calendar.time = format.parseDateTime(datetime).toDate()
        calendar[Calendar.HOUR_OF_DAY] = h
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.SECOND] = sec
        return calendar
    }
}