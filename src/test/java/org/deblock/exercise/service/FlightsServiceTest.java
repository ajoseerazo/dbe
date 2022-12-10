package org.deblock.exercise.service;

import org.deblock.exercise.advice.InvalidNumberOfPassengersException;
import org.deblock.exercise.interfaces.Supplier;
import org.deblock.exercise.model.Flight;
import org.deblock.exercise.util.Fixtures;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightsServiceTest {
    @Autowired
    private FlightsService flightsService;

    @MockBean
    private ToughJetSupplierService toughJetSupplierService;

    @Test
    public void testShouldThrowExceptionIfNumberOfPassengersIsLessThanOne()  {
        flightsService = new FlightsService();

        assertThrows(InvalidNumberOfPassengersException.class, () -> {
            flightsService.searchFlights("MAD", "AMS", LocalDate.now(), LocalDate.now(), 0, new ArrayList<>());
        });
    }

    @Test
    public void testShouldThrowExceptionIfNumberOfPassengersIsMoreThanFour() {
        flightsService = new FlightsService();

        assertThrows(InvalidNumberOfPassengersException.class, () -> {
            flightsService.searchFlights("MAD", "AMS", LocalDate.now(), LocalDate.now(), 5, new ArrayList<>());
        });
    }

    @Test
    void testShouldReturnEmptyListIfSupplierListIsEmpty() {
        flightsService = new FlightsService();

        List<Flight> flights = flightsService.searchFlights("MAD", "AMS", LocalDate.now(), LocalDate.now(), 4, new ArrayList<>());

        assertEquals(0, flights.size());
    }

    @Test
    void testShouldReturnListOfFlightsGivingAListOfSuppliers() throws Exception {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(toughJetSupplierService);

        flightsService = new FlightsService();

        LocalDate departureDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now();
        int numberOfPassengers = 4;
        String origin = "MAD";
        String destination = "AMS";

        Mockito.when(toughJetSupplierService.searchFlights(origin, destination, departureDate, returnDate, numberOfPassengers)).thenReturn(Fixtures.FLIGHTS);

        List<Flight> flights = flightsService.searchFlights(origin, destination, departureDate, returnDate, numberOfPassengers, suppliers);

        assertEquals(Fixtures.FLIGHTS.size(), flights.size());
        assertEquals(Fixtures.FLIGHTS.get(0), flights.get(0));
    }
}
