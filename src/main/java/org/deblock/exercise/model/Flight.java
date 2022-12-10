package org.deblock.exercise.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.deblock.exercise.util.LocalDateTimeToStringConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a Flight into DeblockFlights
 */
public class Flight {
    @JsonProperty
    private String airline;
    @JsonProperty
    private String supplier;
    @JsonProperty
    private BigDecimal fare;
    @JsonProperty
    private String departureAirportCode;
    @JsonProperty
    private String destinationAirportCode;
    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime departureDate;
    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime arrivalDate;

    public Flight(String airline, String supplier, BigDecimal fare, String departureAirportCode, String destinationAirportCode, LocalDateTime departureDate, LocalDateTime arrivalDate) {
        this.airline = airline;
        this.supplier = supplier;
        this.fare = fare;
        this.departureAirportCode = departureAirportCode;
        this.destinationAirportCode = destinationAirportCode;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public BigDecimal getFare() {
        return fare;
    }
    public String getAirline() {
        return airline;
    }
    public String getSupplier() {
        return supplier;
    }
    public String getDepartureAirportCode() {
        return departureAirportCode;
    }
    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }
    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }
}
