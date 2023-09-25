package edu.school21.sockets.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Message {

    private Long id;

    private String text;

    private LocalDateTime time;

    private User sender;
}
