package org.deblock.exercise.controller;

import org.deblock.exercise.advice.InvalidNumberOfPassengersException;
import org.deblock.exercise.model.Flight;
import org.deblock.exercise.service.CrazyAirSupplierService;
import org.deblock.exercise.service.FlightsService;
import org.deblock.exercise.service.ToughJetSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
public class FlightsController {
    @Autowired
    FlightsService flightsService;

    public FlightsController() {

    }

    @GetMapping("/flights/search")
    @ResponseBody
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam
            @NotNull
            @Size(min = 3, max = 3)
            String origin,
            @RequestParam
            @NotNull
            @Size(min = 3, max = 3)
            String destination,
            @RequestParam
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @NotNull LocalDate departureDate,
            @RequestParam
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate returnDate,
            @RequestParam
            @Min(1)
            @Max(4)
            @NotNull
            int numberOfPassengers) throws InvalidNumberOfPassengersException {
        CrazyAirSupplierService crazyAirSupplier = new CrazyAirSupplierService();
        ToughJetSupplierService toughJetSupplier = new ToughJetSupplierService();

        List<Flight> results = flightsService.searchFlights(
                origin,
                destination,
                departureDate,
                returnDate,
                numberOfPassengers,
                Arrays.asList(crazyAirSupplier, toughJetSupplier));

        return ResponseEntity.ok(results);
    }
}
