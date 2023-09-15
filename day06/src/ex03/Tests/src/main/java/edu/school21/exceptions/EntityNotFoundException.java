package edu.school21.exceptions;

import java.sql.SQLException;

public class EntityNotFoundException extends SQLException {

    public EntityNotFoundException(String reason) {
        super(reason);
    }
}