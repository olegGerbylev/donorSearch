package donor.endpoints.validation


import donor.endpoints.errors.ApiError
import donor.endpoints.errors.ApiErrorCode
import donor.endpoints.errors.WebException
import donor.utils.ClockHolder
import donor.utils.DateUtils
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern
import java.util.regex.Pattern.CASE_INSENSITIVE

private val EMAIL_PATTERN = Pattern.compile(
    """^(?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])$""",
    CASE_INSENSITIVE
)

class Validator(val data: Any) {

    constructor(data: Any, noCheck: Boolean = false, checker: Validator.() -> Unit) : this(data) {
        this.checker()
        if (!noCheck) {
            check()
        }
    }

    private val errors = ArrayList<ApiError>()
    fun error(field: String, code: ApiErrorCode, vararg pairs: Pair<String, Any>) {
        val args = mapOf("field" to field) + pairs.toMap()
        errors.add(ApiError(code, args))
    }

    private fun check() {
        if (errors.isNotEmpty()) {
            throw WebException(errors)
        }
    }

    fun isNull(field: String) {
        val v = getValue(field)
        if (v != null) {
            error(field, ApiErrorCode.VALIDATION_MUST_BE_NULL)
        }
    }

    fun isEqualToPath(field: String, value: Any) {
        val v = getValue(field)
        if (v == null) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL)
        }
        if (v != value) {
            error(field, ApiErrorCode.VALIDATION_MUST_BE_EQUAL_TO_PATH)
        }
    }

    fun isNotNull(field: String) {
        val v = getValue(field)
        if (v == null) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL)
        }
    }

    fun isNotNullOrEmpty(field: String) {
        val v = getValue(field)
        if (v == null || (v is String && v.isEmpty())) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL_AND_EMPTY)
        }
    }

    fun isTimeString(field: String, optional: Boolean = false): Instant? {
        val v = getValue(field)
        if (v == null && optional) {
            // no error
        } else if (v == null || v !is String) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL)
        } else {
            try {
                return DateUtils.fromISOString(v)
            } catch (e: Exception) {
                error(field, ApiErrorCode.VALIDATION_MUST_BE_ISO_TIME)
            }
        }
        return null
    }

    fun dayInFuture(field: String): Instant? {
        val time = isTimeString(field)
        if (time != null) {
            if (time.truncatedTo(ChronoUnit.DAYS) < Instant.now().truncatedTo(ChronoUnit.DAYS)) {
                error(field, ApiErrorCode.VALIDATION_MUST_BE_TIME_IN_FUTURE)
            }
        }
        return time
    }

    fun timeInFuture(field: String): Instant? {
        val time = isTimeString(field)
        if (time != null) {
            if (time < ClockHolder.instantNow()) {
                error(field, ApiErrorCode.VALIDATION_MUST_BE_TIME_IN_FUTURE)
            }
        }
        return time
    }

    fun timeGreaterThan(field: String, otherTime: Instant): Instant? {
        val time = isTimeString(field)
        if (time != null) {
            if (time.toEpochMilli() < otherTime.toEpochMilli()) {
                error(field, ApiErrorCode.VALIDATION_INCORRECT_TIME)
            }
        }
        return time
    }

    fun length(field: String, min: Int = 0, max: Int = 0, optional: Boolean = false) {
        val v = getValue(field)
        if (v.isNullOrEmpty() && optional) {
            // no error
        } else if (v == null || v !is String) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL)
        } else if (min != 0 && v.length < min) {
            error(field, ApiErrorCode.VALIDATION_MIN_LENGTH, "min" to min)
        } else if (max != 0 && v.length > max) {
            error(field, ApiErrorCode.VALIDATION_MAX_LENGTH, "max" to max)
        }
    }

    fun range(field: String, min: Int? = null, max: Int? = null, optional: Boolean = false) {
        val v = getValue(field)
        if (v == null && optional) {
            // no error
        } else if (v == null || v !is Number) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL)
        } else if (min != null && v.toInt() < min) {
            error(field, ApiErrorCode.VALIDATION_MIN, "min" to min)
        } else if (max != null && v.toInt() > max) {
            error(field, ApiErrorCode.VALIDATION_MAX, "max" to max)
        }
    }


    private fun getValue(field: String, data: Any = this.data): Any? {
        when {
            field.indexOf("[") != -1 -> {
                val i = field.indexOf("[")
                val first = field.substring(0, i)
                val index = field[i + 1]
                return if (field.indexOf(".") == -1) {
                    val f = data.javaClass.getDeclaredField(first)
                    f.isAccessible = true
                    val v = f.get(data)

                    (v as ArrayList<*>)[Character.getNumericValue(index)]
                } else {
                    val second = field.substring(field.indexOf(".") + 1, field.length)
                    val f = data.javaClass.getDeclaredField(first)
                    f.isAccessible = true
                    val v = f.get(data)
                    val t = (v as ArrayList<*>)[Character.getNumericValue(index)]
                    t?.let { getValue(second, it) }
                }
            }
            field.indexOf(".") == -1 -> {
                val f = data.javaClass.getDeclaredField(field)
                f.isAccessible = true
                return f.get(data)
            }
            else -> {
                val i = field.indexOf(".")
                val first = field.substring(0, i)
                val second = field.substring(i + 1, field.length)
                val f = data.javaClass.getDeclaredField(first)
                f.isAccessible = true
                val v = f.get(data)
                return getValue(second, v)
            }
        }
    }

    fun email(field: String, optional: Boolean = false) {
        val v = getValue(field)
        v.isNullOrEmpty()
        if (v.isNullOrEmpty() && optional) {
            // no error
        } else if (v.isNullOrEmpty() || v !is String) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL)
        } else if (!EMAIL_PATTERN.matcher(v).matches()) {
            error(field, ApiErrorCode.VALIDATION_EMAIL)
        }
    }

    fun regexp(field: String, regex: Regex, optional: Boolean = false) {
        val v = getValue(field)
        if (v == null && optional) {
            // no error
        } else if (v == null || v !is String) {
            error(field, ApiErrorCode.VALIDATION_MUST_NOT_BE_NULL)
        } else if (!regex.matches(v)) {
            error(field, ApiErrorCode.VALIDATION_PATTERN)
        }
    }
}

private fun Any?.isNullOrEmpty(): Boolean {
    return (this == "" || this == null)
}

