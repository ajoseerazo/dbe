package org.deblock.exercise.interfaces;

import org.deblock.exercise.model.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface that represents a third-party flights results provider
 */
public interface Supplier {
    public abstract List<Flight> searchFlights(String origin, String destination, LocalDate departureDate, LocalDate returnDate, int numberOfPassengers) throws InterruptedException;
}
