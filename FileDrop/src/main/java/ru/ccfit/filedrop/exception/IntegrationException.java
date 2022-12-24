package ru.ccfit.filedrop.exception;

import ru.ccfit.filedrop.enumeration.ErrorCode;

public class IntegrationException extends BasicFileDropException{
    public IntegrationException() {
        super(ErrorCode.NOT_FOUND);
    }

    public IntegrationException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
