package org.deblock.exercise.util;

import org.deblock.exercise.model.Flight;

import java.util.Comparator;

public class FlightsComparatorByFare implements Comparator<Flight> {

    @Override
    public int compare(Flight a, Flight b) {
        return a.getFare().compareTo(b.getFare());
    }
}