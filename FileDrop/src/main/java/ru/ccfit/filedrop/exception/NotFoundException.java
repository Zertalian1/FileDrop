package ru.ccfit.filedrop.exception;

import ru.ccfit.filedrop.enumeration.ErrorCode;

public class NotFoundException extends BasicFileDropException{
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
