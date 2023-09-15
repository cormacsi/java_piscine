package edu.school21.exceptions;

import java.sql.SQLException;

public class NotDeletedSubEntityException extends SQLException {

    public NotDeletedSubEntityException(String reason) {
        super(reason);
    }
}
