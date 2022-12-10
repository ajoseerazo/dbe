package org.deblock.exercise.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.deblock.exercise.model.Flight;
import org.deblock.exercise.service.FlightsService;
import org.deblock.exercise.util.Fixtures;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FlightsService flightsService;

    @Test
    public void searchFlights() throws Exception {
        String origin = "MAD";
        String destination = "AMS";
        LocalDate departureDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now();
        int numberOfPassengers = 2;

        Mockito.when(flightsService.searchFlights(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyList())).thenReturn(Fixtures.FLIGHTS);

        List<Flight> flightResults = Fixtures.FLIGHTS;

        ObjectMapper mapperObj = new ObjectMapper();

        String jsonStr = mapperObj.writeValueAsString(flightResults);

        mvc.perform(MockMvcRequestBuilders.get("/flights/search?origin=" + origin + "&destination=" + destination + "&departureDate=" + departureDate.toString() + "&returnDate=" + returnDate.toString() + "&numberOfPassengers=" + numberOfPassengers).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    @Test
    public void shouldReturnBadRequestIfSearchFlightsWithMissingNumberOfPassengers() throws Exception {
        String origin = "MAD";
        String destination = "AMS";
        LocalDate departureDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now();

        mvc.perform(MockMvcRequestBuilders.get("/flights/search?origin=" + origin + "&destination=" + destination + "&departureDate=" + departureDate.toString() + "&returnDate=" + returnDate.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}