package org.deblock.exercise.service;

import org.deblock.exercise.advice.InvalidNumberOfPassengersException;
import org.deblock.exercise.interfaces.Supplier;
import org.deblock.exercise.model.Flight;
import org.deblock.exercise.util.FlightsComparatorByFare;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that handles the business logic related to Flights
 */
@Service
public class FlightsService {
    /**
     *
     * @param origin Origin place
     * @param destination Destination place
     * @param departureDate Departure date
     * @param returnDate Return Date
     * @param numberOfPassengers Number of passengers
     * @param flightsSuppliers Third party flight suppliers used to find flights
     * @return List of Flights found for the params
     * @throws InvalidNumberOfPassengersException
     */
    public List<Flight> searchFlights(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Integer numberOfPassengers, List<Supplier> flightsSuppliers) throws InvalidNumberOfPassengersException {
        if (numberOfPassengers < 1 || numberOfPassengers > 4) {
            throw new InvalidNumberOfPassengersException("Number of passengers cannot be more than 4 and less than 1");
        }

        List<Flight> result = flightsSuppliers.parallelStream().map(supplier -> {
            try {
                return supplier.searchFlights(origin, destination, departureDate, returnDate, numberOfPassengers);
            } catch (InterruptedException e) {
                return new ArrayList<Flight>();
            }
        }).flatMap(List::stream).sorted(new FlightsComparatorByFare()).toList();

        return result;
    }
}
