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

    @ColumnInfo(name = "payload_mass_kg_sum")
    private int payload_mass_kg_sum;

    @ColumnInfo(name = "payload_mass_lbs_sum")
    private double payload_mass_lbs_sum;

    @ColumnInfo(name = "launch_success")
    private boolean launch_success;

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


    public int getPayload_mass_kg_sum() {
        return payload_mass_kg_sum;
    }

    public void setPayload_mass_kg_sum(int payload_mass_kg) {
        this.payload_mass_kg_sum = payload_mass_kg;
    }

    public double getPayload_mass_lbs_sum() {
        return payload_mass_lbs_sum;
    }

    public void setPayload_mass_lbs_sum(double payload_mass_lbs) {
        this.payload_mass_lbs_sum = payload_mass_lbs;
    }

    public boolean isLaunch_success() {
        return launch_success;
    }

    public void setLaunch_success(boolean launch_success) {
        this.launch_success = launch_success;
    }

    public Launch() {
    }

    public Launch(Response response) {
        flight_number = response.getFlight_number();
        launch_year = response.getLaunch_year();
        launch_date_unix = response.getLaunch_date_unix();
        mission_patch_small = response.getLinks().getMission_patch_small();
        launch_success = response.isLaunch_success();

        payload_mass_kg_sum = 0;
        payload_mass_lbs_sum = 0;
        for (Response.RocketBean.SecondStageBean.PayloadsBean payloads : response.getRocket().getSecond_stage().getPayloads()) {
            payload_mass_kg_sum = +payloads.getPayload_mass_kg();
            payload_mass_lbs_sum = +payloads.getPayload_mass_lbs();
        }

    }

    @Override
    public boolean equals(Object launch) {
        return launch != null && ((Launch) launch).flight_number == flight_number;
    }
}
