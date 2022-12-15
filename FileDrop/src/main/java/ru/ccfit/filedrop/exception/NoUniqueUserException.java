package ru.ccfit.filedrop.exception;

import ru.ccfit.filedrop.enumeration.ErrorCode;

public class NoUniqueUserException extends BasicFileDropException{
    public NoUniqueUserException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public NoUniqueUserException(String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}
