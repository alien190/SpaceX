package com.example.domain.model.launch;

public class DomainLaunchCache {

    private int flight_number;

    public DomainLaunchCache() {
    }

    public int getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    @Override
    public boolean equals(Object launch) {
        return launch != null && ((DomainLaunchCache) launch).flight_number == flight_number;
    }
}
