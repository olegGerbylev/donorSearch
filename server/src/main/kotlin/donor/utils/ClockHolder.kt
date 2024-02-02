package donor.utils

import java.time.Instant
import java.util.*

object ClockHolder : ClockSource {
    private val clockSource: MutableClockSource = MutableClockSource()

    fun getMutableClockSource(): MutableClockSource {
        return clockSource
    }

    fun getClockSource(): ClockSource {
        return clockSource
    }

    override fun currentTimeMillis(): Long {
        return clockSource.currentTimeMillis()
    }

    override fun newDate(): Date {
        return clockSource.newDate()
    }

    override fun instantNow(): Instant {
        return clockSource.instantNow()
    }

    override fun getCalendarInstance(): Calendar {
        return clockSource.getCalendarInstance()
    }
}