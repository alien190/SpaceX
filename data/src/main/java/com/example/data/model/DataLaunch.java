package com.example.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//@Entity(foreignKeys = @ForeignKey(
//        entity = DataImage.class,
//        parentColumns = "id",
//        childColumns = "imageId",
//        onDelete = CASCADE))

@Entity()
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

    private byte[] image;

    @ColumnInfo(name = "imageId")
    private int imageId = 0;

    @ColumnInfo(name = "presskit")
    private String presskit;

    @ColumnInfo(name = "article_link")
    private String article_link;

    @ColumnInfo(name = "video_link")
    private String video_link;

    @ColumnInfo(name = "wikipedia")
    private String wikipedia;

    private List<String> flickr_images;


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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
        presskit = serverResponse.getLinks().getPresskit();
        article_link = serverResponse.getLinks().getArticle_link();
        video_link = serverResponse.getLinks().getVideo_link();
        wikipedia = serverResponse.getLinks().getWikipedia();
        flickr_images = new ArrayList<>();
        flickr_images.addAll(serverResponse.getLinks().getFlickr_images());

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
