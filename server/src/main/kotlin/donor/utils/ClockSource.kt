package donor.utils

import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
    Replace System.currentTimeMillis() with timeSource.currentTimeMillis()
    Replace new Date() with timeSource.newDate()
    Replace Instant.now() with timeSource.instantNow()
    Replace Calendar.getInstance() with timeSource.getCalendarInstance().
 */
interface ClockSource {
    fun currentTimeMillis(): Long
    fun newDate(): Date
    fun instantNow(): Instant
    fun getCalendarInstance(): Calendar
}

class MutableClockSource private constructor(private val delta: AtomicLong) : ClockSource {

    constructor(delta: Long = 0) : this(AtomicLong(delta))

    
    override fun currentTimeMillis(): Long {
        return System.currentTimeMillis() + delta.get()
    }

    override fun newDate(): Date {
        return Date(currentTimeMillis())
    }

    override fun instantNow(): Instant {
        return Instant.ofEpochMilli(currentTimeMillis())
    }

    override fun getCalendarInstance(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis()
        return calendar
    }

    override fun equals(other: Any?): Boolean {
        return if (other is MutableClockSource) {
            delta == other.delta
        } else false
    }

    override fun hashCode(): Int {
        return delta.hashCode()
    }

    override fun toString(): String {
        return "TestClock[${delta.get()}]"
    }

    fun getDelta(): Long {
        return delta.get()
    }

    fun setDelta(offset: Long) {
        delta.set(offset)
    }

    fun plus(plus: Long) {
        delta.addAndGet(plus)
    }

    fun setTimeMillis(ms: Long) {
        setDelta(ms - System.currentTimeMillis())
    }
}
