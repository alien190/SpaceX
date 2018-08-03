package com.example.data.database;

import android.arch.persistence.room.ColumnInfo;

public class LaunchYearStatistic {
    @ColumnInfo(name = "launch_year")
    private String launch_year;

    @ColumnInfo(name = "count")
    private int count;

    @ColumnInfo(name = "payload_mass_kg_sum")
    private int payload_mass_kg_sum;

    @ColumnInfo(name = "payload_mass_lbs_sum")
    private double payload_mass_lbs_sum;

    public String getLaunch_year() {
        return launch_year;
    }

    public void setLaunch_year(String launch_year) {
        this.launch_year = launch_year;
    }

    public int getPayload_mass_kg_sum() {
        return payload_mass_kg_sum;
    }

    public void setPayload_mass_kg_sum(int payload_mass_kg_sum) {
        this.payload_mass_kg_sum = payload_mass_kg_sum;
    }

    public double getPayload_mass_lbs_sum() {
        return payload_mass_lbs_sum;
    }

    public void setPayload_mass_lbs_sum(double payload_mass_lbs_sum) {
        this.payload_mass_lbs_sum = payload_mass_lbs_sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
