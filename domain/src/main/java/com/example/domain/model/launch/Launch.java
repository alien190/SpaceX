package com.example.domain.model.launch;

public class Launch {

    private int flight_number;
    private String launch_year;
    private int launch_date_unix;
    private String mission_patch_small;
    private int payload_mass_kg_sum;
    private double payload_mass_lbs_sum;
    private boolean launch_success;
    private String mission_name;
    private String details;
    private String rocket_name;
    private String launch_date_utc;

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

    public Launch() {
    }

    public Launch(Response response) {
        flight_number = response.getFlight_number();
        launch_year = response.getLaunch_year();
        launch_date_unix = response.getLaunch_date_unix();
        mission_patch_small = response.getLinks().getMission_patch_small();
        launch_success = response.isLaunch_success();
        mission_name = response.getMission_name();
        details = response.getDetails();
        rocket_name = response.getRocket().getRocket_name();
        launch_date_utc = response.getLaunch_date_utc();

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
