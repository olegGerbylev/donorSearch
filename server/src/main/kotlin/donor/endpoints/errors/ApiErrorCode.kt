package donor.endpoints.errors

import org.springframework.http.HttpStatus

class ApiError(
    val error: ApiErrorCode,
    val args: Map<String, Any> = emptyMap()
) {
    constructor(error: ApiErrorCode, ex: Exception):
            this(error, mapOf("message" to (ex.message ?: ""), "class" to ex.javaClass.name))

    constructor(error: ApiErrorCode, vararg pairs: Pair<String, Any>):
            this(error, pairs.toMap())
}

enum class ApiErrorCode (val message: String, val status: HttpStatus, val dbErrorKey: String? = null) {
    INTERNAL_SERVER_ERROR(
            "Внутренняя ошибка системы",
            HttpStatus.INTERNAL_SERVER_ERROR),

    POSITION_NOT_EXIST(
        "Данной позиции не существует",
        HttpStatus.BAD_REQUEST),

    SOMETHING_GO_WRONG(
        "Что то пошло не так",
        HttpStatus.I_AM_A_TEAPOT
    ),

    ROTTEN_TOKEN(
        "Не коректный или просроченый токен",
        HttpStatus.BAD_REQUEST),

    AUTHENTICATION_REQUIRED(
            "Для доступа к этому ресурсу необходима аутентификация",
            HttpStatus.UNAUTHORIZED),

    INVALID_ACCESS_TOKEN(
            "Некорректный токен доступа",
            HttpStatus.FORBIDDEN),

    INVALID_ACCESS_TOKEN_FOR_ACCOUNT(
            "Токен доступа не соответствует указанному в URL аккаунту",
            HttpStatus.FORBIDDEN
    ),

    ACCOUNT_INACTIVE(
            "Аккаунт деактивирован. Обратитесь в службу поддержки",
            HttpStatus.FORBIDDEN),

    SESSION_EXPIRED(
            "Время сессии истекло, требуется аутентификация",
            HttpStatus.FORBIDDEN),

    AUTH_FAILED(
            "Email или пароль не верны",
            HttpStatus.FORBIDDEN),

    AUTH_FAILED_RECEPTION(
            "Код мероприятия или пин-код не верны",
            HttpStatus.FORBIDDEN),

    DESK_UNAVAILABLE_DAY(
            "Стойка недоступна в текущий день",
            HttpStatus.FORBIDDEN),

    UNSUPPORTED_MEDIA_TYPE(
            "Unsupported media type \${type}, supported types: \${supported}",
            HttpStatus.UNSUPPORTED_MEDIA_TYPE),

    HANDLER_NOT_FOUND(
            "No handler found for \${method} \${uri}",
            HttpStatus.NOT_FOUND),

    RESOURCE_NOT_FOUND(
            "No resource found for type=\${entity}, id=\${id}, uri=\${uri}",
            HttpStatus.NOT_FOUND),

    DATA_INTEGRITY_VIOLATION(
            "Data integrity violation",
            HttpStatus.BAD_REQUEST
    ),

    EMAIL_ALREADY_EXIST(
        "Пользователь с такой почтой уже существует",
        HttpStatus.BAD_REQUEST
    ),

    EMAIL_NOT_EXIST(
        "Пользователя с такой почтой не существует",
        HttpStatus.BAD_REQUEST
    ),

    VALIDATION_EMAIL(
            "Некорректный формат почтового адреса",
            HttpStatus.BAD_REQUEST
    ),

    EMAIL_ALIASED(
        "Используйте основной вид почтового адреса, без \"+суффикс\" дополнений",
        HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MUST_BE_NULL(
            "Поле должно быть null",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MUST_BE_EQUAL_TO_PATH(
            "Значение поля в теле запроса должно соответствовать зачению в url",
            HttpStatus.BAD_REQUEST
    ),

    TOKEN_MUST_BE_EXIST(
        "токен деактивирован",
        HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MUST_NOT_BE_NULL(
            "Поле является обязательным",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MUST_NOT_BE_NULL_AND_EMPTY(
            "Поле является обязательным и не должно быть пустой строкой",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_ENTITY_NOT_FOUND(
            "Значение не найдено",
            HttpStatus.BAD_REQUEST
    ),

    QR_TOKEN_SHOULD_CONTAINS_LETTERS_AND_DIGITS(
            "QR код может состоять только из цифр и латинских букв",
            HttpStatus.BAD_REQUEST
    ),

    QR_TOKEN_SHOULD_BE_UNIQUE(
            "QR код должен быть уникальным",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_IMAGE_NOT_FOUND(
            "Изображение не найдено",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_IMAGE_SIZE(
            "Размер загружаемого изображения не должен превышать \${size} Mb",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_IMAGE_TYPE(
            "Неверный формат изображения. Загрузите изображение в формате \${format}",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_IMAGE_RESOLUTION(
            "Неверное разрешение изображения. Загрузите изображение не более \${resolution}",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_IMAGE_CANNOT_READ(
            "Невозможно обработать изображение",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MIN_LENGTH(
            "Минимальная длина значения составляет \${min} символа",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MAX_LENGTH(
            "Максимальная длина значения составляет \${max} символов",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MUST_BE_ISO_TIME(
            "Поле должно содержать время в ISO формате, например: 2019-12-25T22:22:22Z",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MUST_BE_TIME_IN_FUTURE(
            "Значение должно быть временем в будущем",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_DAYS_PERIOD_EXCEEDED(
            "Период активности стойки не может превышать \${max} дней",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_INCORRECT_TIME(
            "Значение не удовлетворяет ограничениям",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_INCORRECT_VALUE(
            "Значение не удовлетворяет ограничениям",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_INCORRECT_AMOUNT(
            "Некорректный формат суммы для изменения баланса",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MIN(
            "Минимум \${min}",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_MAX(
            "Максимум \${max}",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_PHONE(
            "Некорректный формат телефона",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_PATTERN(
            "Значение не соответствует разрешённому шаблону",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_JSON_FORMAT(
            "Значение не соответствует разрешённому json-формату",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_SYSTEM_FIELD_REQUIRED(
            "Системное поле \${systemField} обязательно должно присутствовать в массиве fields",
            HttpStatus.BAD_REQUEST
    ),

    ACCOUNT_NOT_FOUND(
        "Аккаунт не найден",
        HttpStatus.BAD_REQUEST
    ),

    VALIDATION_DUPLICATE_FIELD(
            "Поле с именем переменной \${name} уже существует",
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_USER_DOESNT_MATCH_ACCOUNT(
      "Указанные accountId и userId не соответствуют друг другу",
            HttpStatus.BAD_REQUEST
    ),

    NO_PERMISSION(
            "Нет прав для доступа к ресурсу. Требуется разрешение \${permission}",
            HttpStatus.UNAUTHORIZED
    ),

    NO_FEATURE(
            "Нет прав для доступа к ресурсу. Требуется разрешение \${feature}",
            HttpStatus.UNAUTHORIZED
    ),

    DESK_LIMIT_EXCEEDED(
            "Не удалось войти в стойку \${deskName} из-за тарифных ограничений. Обратитесь к администратору.",
            HttpStatus.PAYMENT_REQUIRED
    ),

    DESKS_LIMIT_EXCEEDED(
            "Исчерпан лимит на стойки (разрешено: \${max}, использовано в этом месяце \${current})." +
                    " Перейдите на другой тариф, чтобы добавлять больше стоек.",
            HttpStatus.PAYMENT_REQUIRED
    ),

    VISITOR_LIMIT_EXCEEDED(
            "Исчерпан лимит на посетителей (разрешено: \${max}, использовано в этом месяце \${current})." +
                    " Перейдите на другой тариф, чтобы добавлять больше посетителей.",
            HttpStatus.PAYMENT_REQUIRED
    ),


    VISITOR_LIMIT_EXCEEDED_ON_IMPORT(
            "Превышен лимит посетителей (хотите загрузить: \${count}, использовано в этом месяце: " +
                    "\${current}, разрешено: \${max}). Перейдите на другой тариф, чтобы загрузить больше посетителей",
            HttpStatus.PAYMENT_REQUIRED
    ),

    SMS_LIMIT_EXCEEDED(
            "Исчерпан лимит на смс-сообщения (разрешено: \${max}, сейчас использовано \${current})." +
                    " Перейдите на другой тариф, чтобы отправлять больше смс-сообщений.",
            HttpStatus.PAYMENT_REQUIRED
    ),

    MEETUPS_LIMIT_EXCEEDED(
            "Исчерпан лимит на мероприятия (разрешено: \${max}, использовано в этом месяце \${current})." +
                    " Перейдите на другой тариф, чтобы создавать больше мероприятий.",
            HttpStatus.PAYMENT_REQUIRED
    ),

    INSUFFICIENT_FUNDS(
            "Не хватает денег. Стоимость: \${price}, на балансе: \${balance}",
            HttpStatus.PAYMENT_REQUIRED
    ),

    VISITOR_DUPLICATE(
            "Посетитель с такими данными уже есть",
            HttpStatus.BAD_REQUEST
    ),

    VISITORS_DUPLICATE(
            "Нарушено условие уникальности",
            HttpStatus.BAD_REQUEST
    ),

    INCORRECT_PAYMENT_DESCRIPTION(
            "Описание платежа содержит недопустимые символы",
            HttpStatus.BAD_REQUEST
    ),

    INCORRECT_REDIRECT_URL(
      "Ссылка для редиректа не должна вести на сторонний ресурс",
            HttpStatus.BAD_REQUEST
    ),

    PAYMENT_DATA_INTEGRITY_VIOLATION(
            "Нарушение целостности данных платежа",
            HttpStatus.BAD_REQUEST
    ),

    PAYMENT_CANT_BE_PROCESSED(
            "Платеж не может быть совершен",
            HttpStatus.BAD_REQUEST
    ),

    PAYMENT_WITH_INCORRECT_GOAL(
            "Указана неверная цель платежа",
            HttpStatus.BAD_REQUEST
    ),

    PAYMENT_AMOUNT_DONT_MATCH_PRICE(
            "Сумма платежа не совпадает с ценой покупки",
            HttpStatus.BAD_REQUEST
    ),

    PAYMENT_AMOUNT_INCORRECT(
            "Сумма платежа должна быть положительной",
            HttpStatus.BAD_REQUEST
    ),

    PRICE_INCORRECT(
        "Цена должна быть положительной",
        HttpStatus.BAD_REQUEST
    ),

    CARD_IN_TIMEOUT(
            "На данный момент фоновые платежи невозможны, повторите попытку позже",
            HttpStatus.BAD_REQUEST
    ),

    LINKED_CARD_DOESNT_EXIST(
            "К аккаунту не привязана карта",
            HttpStatus.BAD_REQUEST
    ),

    TELECHAT_UNAVAILABLE(
            "Сервис telechat недоступен",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    NO_VISITOR_FOR_EMAIL(
            "Нет ни одного посетителя, чтобы сформировать тестовое письмо",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    CANNOT_HIDE_REQUIRED_FIELD(
            "Обязательное поле \${fields} нельзя скрыть с регистрационной формы",
            HttpStatus.BAD_REQUEST
    ),

    CANNOT_CREAT_CHAT_DEMO(
        "В демо аккаунте невозможно добавить чат",
        HttpStatus.BAD_REQUEST
    ),

    CANNOT_DISABLE_TELECHAT_DEMO(
        "В демо аккаунте невозможно отключить операторов",
        HttpStatus.BAD_REQUEST
    ),

    ALREADY_PUBLISHED(
            "Редактировать можно только новости в статусе Черновик",
            HttpStatus.BAD_REQUEST
    ),

    TEXT_OR_TITLE_SHOULD_NOT_BE_NULL(
            "Должен быть указан заголовок или текст",
            HttpStatus.BAD_REQUEST
    ),

    CANNOT_SEND_QR_CODE_TO_UNREGISTER(
        "QR-code может быть отправлен только зарегистрированным пользователям",
        HttpStatus.BAD_REQUEST
    ),

    CANNOT_DELETE_DEFAULT_GROUP(
            "Нельзя удалить группу по умолчанию. Проверьте настройки полей",
            HttpStatus.BAD_REQUEST
    ),

    VISITORS_NOT_FOUND(
            "Посетители не найдены: \${emails}",
            HttpStatus.BAD_REQUEST
    ),


}
