package com.example.eschoolapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    String latitude,longtitude,category,space,timing;


    public Location(String latitude, String longtitude, String category, String timing, String space) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.category = category;
        this.timing = timing;
        this.space = space;
    }
    protected Location(Parcel in){
        latitude = in.readString();
        longtitude = in.readString();
        category = in.readString();
        timing = in.readString();
        space = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(latitude);
    parcel.writeString(longtitude);
    parcel.writeString(category);
    parcel.writeString(timing);
    parcel.writeString(space);
    }
}
