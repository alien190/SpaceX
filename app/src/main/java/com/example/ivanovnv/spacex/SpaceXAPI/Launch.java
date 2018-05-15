package com.example.ivanovnv.spacex.SpaceXAPI;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Launch {

    @ColumnInfo(name = "flight_number")
    @PrimaryKey
    private int flight_number;

    @ColumnInfo(name = "launch_year")
    private String launch_year;

    @ColumnInfo(name = "launch_date_unix")
    private int launch_date_unix;

    @ColumnInfo(name = "mission_patch_small")
    private String mission_patch_small;

    public int getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    public String getLaunch_year() {
        return launch_year;
    }

    public void setLaunch_year(String launch_year) {
        this.launch_year = launch_year;
    }

    public int getLaunch_date_unix() {
        return launch_date_unix;
    }

    public void setLaunch_date_unix(int launch_date_unix) {
        this.launch_date_unix = launch_date_unix;
    }

    public String getMission_patch_small() {
        return mission_patch_small;
    }

    public void setMission_patch_small(String mission_patch_small) {
        this.mission_patch_small = mission_patch_small;
    }

    public Launch() {
    }

    public Launch(Response response) {
        flight_number = response.getFlight_number();
        launch_year = response.getLaunch_year();
        launch_date_unix = response.getLaunch_date_unix();
        mission_patch_small = response.getLinks().getMission_patch_small();
    }

    @Override
    public boolean equals(Object launch) {
        return launch != null && ((Launch) launch).flight_number == flight_number;
    }
}
