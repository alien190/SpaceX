package com.example.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DataLaunchCache {
    @ColumnInfo(name = "flight_number")
    @PrimaryKey
    private int flight_number;

//    @ColumnInfo(name = "mission_patch_small")
//    private String mission_patch_small;

    public DataLaunchCache() {
    }

    public DataLaunchCache(ServerResponse serverResponse) {
        flight_number = serverResponse.getFlight_number();
        //mission_patch_small = serverResponse.getLinks().getMission_patch_small();
    }

    public int getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

//    public String getMission_patch_small() {
//        return mission_patch_small;
//    }
//
//    public void setMission_patch_small(String mission_patch_small) {
//        this.mission_patch_small = mission_patch_small;
//    }
}
