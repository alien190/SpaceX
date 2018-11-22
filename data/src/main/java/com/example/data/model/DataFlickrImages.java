package com.example.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = DataLaunch.class,
        parentColumns = "flight_number",
        childColumns = "flight_number"))
public class DataFlickrImages {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int flight_number;
    private String imageUrl;
}
