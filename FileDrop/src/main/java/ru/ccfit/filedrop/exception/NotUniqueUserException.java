package ru.ccfit.filedrop.exception;

import ru.ccfit.filedrop.enumeration.ErrorCode;

public class NotUniqueUserException extends BasicFileDropException{
    public NotUniqueUserException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public NotUniqueUserException(String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}
