package edu.school21.exceptions;

import java.sql.SQLException;

public class AlreadyAuthenticatedException extends SQLException {

    public AlreadyAuthenticatedException(String reason) {
        super(reason);
    }
}
