package org.deblock.exercise.service;

import org.deblock.exercise.interfaces.Supplier;
import org.deblock.exercise.model.Flight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrazyAirSupplierService implements Supplier {
    static final String SUPPLIER_NAME = "CrazyAir";

    /**
     * Function to mock the api call to the flights provider
     * @param path
     * @param params
     * @return
     * @throws InterruptedException
     */
    private List<Map<String, String>> apiCallMock(String path, Map<String, Object> params) throws InterruptedException {
        Thread.sleep(5000);

        List<Map<String, String>> results = new ArrayList<>();

        Map<String, String> result = new HashMap<>();

        result.put("airline", "Latam");
        result.put("price", "100.89");
        result.put("cabinclass", "business");
        result.put("departureAirportCode", "MAD");
        result.put("destinationAirportCode", "AMS");
        result.put("departureDate", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        result.put("arrivalDate", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        results.add(result);

        return results;
    }

    Map<String, Object> buildRequestParams(String origin, String destination, LocalDate departureDate, LocalDate returnDate, int numberOfPassengers) {
        Map<String, Object> params = new HashMap<>();
        params.put("origin", origin);
        params.put("destination", destination);
        params.put("departureDate", departureDate.toString());
        params.put("returnDate", returnDate.toString());
        params.put("passengerCount", numberOfPassengers);

        return params;
    }

    /**
     * Method to search flights for provided by this supplier
     * @param origin Origin place
     * @param destination Destination place
     * @param departureDate Departure date
     * @param returnDate Return date
     * @param numberOfPassengers Number of passengers
     * @return
     * @throws InterruptedException
     */
    @Override
    public List<Flight> searchFlights(String origin, String destination, LocalDate departureDate, LocalDate returnDate, int numberOfPassengers) throws InterruptedException {
        Map<String, Object> params = buildRequestParams(origin, destination, departureDate, returnDate, numberOfPassengers);

        List<Map<String, String>> results = apiCallMock("/crazy-air/search", params);

        List<Flight> resultsAdapted = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            resultsAdapted.add(fromHashMapToFlight(results.get(i)));
        }

        return resultsAdapted;
    }

    /**
     * Method that transform the response of the api to a Flight object
     * @param map
     * @return
     */
    public Flight fromHashMapToFlight(Map<String, String> map) {
        DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime departureDate = LocalDateTime.parse(map.get("departureDate"), f);
        LocalDateTime returnDate = LocalDateTime.parse(map.get("arrivalDate"), f);

        BigDecimal price = new BigDecimal(map.get("price")).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        Flight flight = new Flight(map.get("airline"), SUPPLIER_NAME, price, map.get("departureAirportCode"), map.get("destinationAirportCode"), departureDate, returnDate);

        return flight;
    }
}
