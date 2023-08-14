package org.example.exceptions;

public class IncorrectData extends RuntimeException {
    public IncorrectData(String message) {
        super(message);
    }
}
