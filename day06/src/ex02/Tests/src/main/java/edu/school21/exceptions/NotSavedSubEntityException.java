package edu.school21.exceptions;

import java.sql.SQLException;

public class NotSavedSubEntityException extends SQLException {

    public NotSavedSubEntityException(String reason) {
        super(reason);
    }
}
