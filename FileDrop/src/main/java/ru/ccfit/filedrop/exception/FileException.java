package ru.ccfit.filedrop.exception;

import ru.ccfit.filedrop.enumeration.ErrorCode;

public class FileException extends BasicFileDropException{
    public FileException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public FileException(String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}
