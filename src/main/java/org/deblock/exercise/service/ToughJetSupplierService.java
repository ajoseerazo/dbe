package org.deblock.exercise.service;

import org.deblock.exercise.interfaces.Supplier;
import org.deblock.exercise.model.Flight;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToughJetSupplierService implements Supplier {
    static final String SUPPLIER_NAME = "ToughJet";

    /**
     * Function to mock the api call to the flights provider
     * @param path
     * @param params
     * @return
     * @throws InterruptedException
     */
    private List<Map<String, String>> apiCallMock(String path, Map<String, Object> params) throws InterruptedException {
        Thread.sleep(2000);

        List<Map<String, String>> results = new ArrayList<>();

        HashMap<String, String> result1 = new HashMap<>();
        result1.put("carrier", "Latam");
        result1.put("basePrice", "200.18");
        result1.put("tax", "10.12");
        result1.put("discount", "2");
        result1.put("departureAirportName", "MAD");
        result1.put("arrivalAirportName", "AMS");

        Instant instant = Instant.now();

        result1.put("outboundDateTime", DateTimeFormatter.ISO_INSTANT.format(instant));
        result1.put("inboundDateTime", DateTimeFormatter.ISO_INSTANT.format(instant));

        HashMap<String, String> result2 = new HashMap<>();
        result2.put("carrier", "Iberia");
        result2.put("basePrice", "100.23");
        result2.put("tax", "10.12");
        result2.put("discount", "50");
        result2.put("departureAirportName", "MAD");
        result2.put("arrivalAirportName", "AMS");
        result2.put("outboundDateTime", DateTimeFormatter.ISO_INSTANT.format(instant));
        result2.put("inboundDateTime", DateTimeFormatter.ISO_INSTANT.format(instant));

        results.add(result1);
        results.add(result2);

        return results;
    }

    /**
     * Method to build params required for this supplier
     * @param origin
     * @param destination
     * @param departureDate
     * @param returnDate
     * @param numberOfPassengers
     * @return
     */
    Map<String, Object> buildRequestParams(String origin, String destination, LocalDate departureDate, LocalDate returnDate, int numberOfPassengers) {
        Map<String, Object> params = new HashMap<>();
        params.put("from", origin);
        params.put("to", destination);
        params.put("outboundDate", departureDate.toString());
        params.put("inboundDate", returnDate.toString());
        params.put("numberOfAdults", numberOfPassengers);

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

        List<Map<String, String>> results = apiCallMock("/though-jet/flights", params);

        List<Flight> resultsAdapted = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            resultsAdapted.add(fromHashMapToFlight(results.get(i)));
        }

        return resultsAdapted;
    }

    private BigDecimal getFinalPrice(BigDecimal basePrice, BigDecimal tax, BigDecimal discount) {
        BigDecimal netPrice = basePrice.add(tax);
        BigDecimal price = netPrice.subtract(netPrice.multiply(discount).divide(BigDecimal.valueOf(100))).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        return price;
    }

    /**
     * Method that transform the response of the api to a Flight object
     * @param map
     * @return
     */
    public Flight fromHashMapToFlight(Map<String, String> map) {
        BigDecimal basePrice = new BigDecimal(map.get("basePrice"));
        BigDecimal tax = new BigDecimal(map.get("tax"));
        BigDecimal discount = new BigDecimal(map.get("discount"));

        BigDecimal price = getFinalPrice(basePrice, tax, discount);

        DateTimeFormatter f = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());;

        LocalDateTime departureDate = LocalDateTime.parse(map.get("outboundDateTime"), f);
        LocalDateTime returnDate = LocalDateTime.parse(map.get("inboundDateTime"), f);

        Flight flight = new Flight(map.get("carrier"), SUPPLIER_NAME, price, map.get("departureAirportName"), map.get("arrivalAirportName"), departureDate, returnDate);

        return flight;
    }
}
