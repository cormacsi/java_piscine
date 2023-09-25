package edu.school21.sockets.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

}
