package ru.ccfit.filedrop.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "НЕВЕРНЫЙ ЗАПРОС"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ПОЛЬЗОВАТЕЛЬ НЕ АВТОРИЗОВАН"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "ЗАПРОС ЗАПРЕЩЕН"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "ОТСУТСТВУЮТ ПРАВА ДОСТУПА"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ВНУТРЕНЯЯ ОШИБКА СЕРВЕРА"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "СЕРВИС НЕДОСТУПЕН"),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "ВРЕМЯ ОЖИДАНИЯ ИСТЕКЛО");

    private final HttpStatus httpStatus;
    private final String errorMessage;
}
