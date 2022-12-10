package org.deblock.exercise.advice;

public class InvalidNumberOfPassengersException extends RuntimeException {
    public InvalidNumberOfPassengersException(String errorMessage) {
        super(errorMessage);
    }
}