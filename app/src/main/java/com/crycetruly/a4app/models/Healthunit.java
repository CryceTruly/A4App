package com.crycetruly.a4app.models;

/**
 * Created by Elia on 2/1/2018.
 */

public class Healthunit {
    private String added,added_by,phone,name,province,lat,lng,locality,photo,description,open;

    public Healthunit() {
    }

    public Healthunit(String added, String added_by, String phone, String name, String province, String lat, String lng, String locality, String photo, String description, String open) {
        this.added = added;
        this.added_by = added_by;
        this.phone = phone;
        this.name = name;
        this.province = province;
        this.lat = lat;
        this.lng = lng;
        this.locality = locality;
        this.photo = photo;
        this.description = description;
        this.open = open;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


    @Override
    public String toString() {
        return "Healthunit{" +
                "added='" + added + '\'' +
                ", added_by='" + added_by + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", locality='" + locality + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
