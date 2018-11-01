package com.example.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//@Entity(foreignKeys = @ForeignKey(
//        entity = DataImage.class,
//        parentColumns = "id",
//        childColumns = "imageId",
//        onDelete = CASCADE))

@Entity
public class DataLaunch {

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
    private double payload_mass_kg_sum;

    @ColumnInfo(name = "payload_mass_lbs_sum")
    private double payload_mass_lbs_sum;

    @ColumnInfo(name = "launch_success")
    private boolean launch_success;

    @ColumnInfo(name = "mission_name")
    private String mission_name;

    @ColumnInfo(name = "details")
    private String details;

    @ColumnInfo(name = "rocket_name")
    private String rocket_name;

    @ColumnInfo(name = "launch_date_utc")
    private String launch_date_utc;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    // @ColumnInfo(name = "imageId")
    // private int imageId;

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


    public double getPayload_mass_kg_sum() {
        return payload_mass_kg_sum;
    }

    public void setPayload_mass_kg_sum(double payload_mass_kg) {
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

    public String getMission_name() {
        return mission_name;
    }

    public void setMission_name(String mission_name) {
        this.mission_name = mission_name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRocket_name() {
        return rocket_name;
    }

    public void setRocket_name(String rocket_name) {
        this.rocket_name = rocket_name;
    }

    public String getLaunch_date_utc() {
        return launch_date_utc;
    }

    public void setLaunch_date_utc(String launch_date_utc) {
        this.launch_date_utc = launch_date_utc;
    }

    public DataLaunch() {
    }

//    public int getImageId() {
//        return imageId;
//    }
//
//    public void setImageId(int imageId) {
//        this.imageId = imageId;
//    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public DataLaunch(ServerResponse serverResponse) {
        flight_number = serverResponse.getFlight_number();
        launch_year = serverResponse.getLaunch_year();
        launch_date_unix = serverResponse.getLaunch_date_unix();
        mission_patch_small = serverResponse.getLinks().getMission_patch_small();
        launch_success = serverResponse.isLaunch_success();
        mission_name = serverResponse.getMission_name();
        details = serverResponse.getDetails();
        rocket_name = serverResponse.getRocket().getRocket_name();
        launch_date_utc = serverResponse.getLaunch_date_utc();

        payload_mass_kg_sum = 0;
        payload_mass_lbs_sum = 0;
        for (ServerResponse.RocketBean.SecondStageBean.PayloadsBean payloads : serverResponse.getRocket().getSecond_stage().getPayloads()) {
            payload_mass_kg_sum = +payloads.getPayload_mass_kg();
            payload_mass_lbs_sum = +payloads.getPayload_mass_lbs();
        }

    }

    @Override
    public boolean equals(Object launch) {
        return launch != null && ((DataLaunch) launch).flight_number == flight_number;
    }
}
