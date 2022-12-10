package org.deblock.exercise.util;

import org.deblock.exercise.model.Flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Fixtures {
    public static final List<Flight> FLIGHTS = Arrays.asList(new Flight("Latam", "ToughJet", BigDecimal.valueOf(20), "MAD", "AMS", LocalDateTime.now(), LocalDateTime.now()));
}
