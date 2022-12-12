package ru.ccfit.filedrop.exception;

import lombok.Getter;
import ru.ccfit.filedrop.enumeration.ErrorCode;

@Getter
public abstract class BasicFileDropException extends RuntimeException {
    private final ErrorCode errorCode;

    protected BasicFileDropException(ErrorCode errorCode) {
        this(errorCode, errorCode.getErrorMessage());
    }

    protected BasicFileDropException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
