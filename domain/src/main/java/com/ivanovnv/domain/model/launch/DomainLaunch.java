package com.ivanovnv.domain.model.launch;

import java.util.List;

public class DomainLaunch {

    private int flight_number;
    private String launch_year;
    private int launch_date_unix;
    private String mission_patch_small;
    private double payload_mass_kg_sum;
    private double payload_mass_lbs_sum;
    private boolean launch_success;
    private String mission_name;
    private String details;
    private String rocket_name;
    private String launch_date_utc;
    private byte[] image;
    private int imageId;
    private String presskit;
    private String article_link;
    private String video_link;
    private String wikipedia;
    private List<String> flickr_images;
    private String orbit;
    private String nationality;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPresskit() {
        return presskit;
    }

    public void setPresskit(String presskit) {
        this.presskit = presskit;
    }

    public String getArticle_link() {
        return article_link;
    }

    public void setArticle_link(String article_link) {
        this.article_link = article_link;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public List<String> getFlickr_images() {
        return flickr_images;
    }

    public void setFlickr_images(List<String> flickr_images) {
        this.flickr_images = flickr_images;
    }

    public DomainLaunch() {
    }

    @Override
    public boolean equals(Object launch) {
        return launch != null && ((DomainLaunch) launch).flight_number == flight_number;
    }

    public String getOrbit() {
        return orbit;
    }

    public void setOrbit(String orbit) {
        this.orbit = orbit;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
