package com.crycetruly.a4app.models;

/**
 * Created by Elia on 2/7/2018.
 */

public class Councillor {
    private String name,profile,photo,gender;
    private Boolean away;

    public Councillor() {
    }

    public Councillor(String name, String profile, String photo, String gender, boolean away) {
        this.name = name;
        this.profile = profile;
        this.photo = photo;
        this.gender = gender;
        this.away = away;
    }

    public boolean isAway() {
        return away;
    }

    public void setAway(boolean away) {
        this.away = away;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
