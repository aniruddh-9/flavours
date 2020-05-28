package com.example.flavours;

import com.google.firebase.storage.StorageReference;

public class TodayProvider {
    String id;
    String dname;
    String uname;
    String itemname;
    String starttime;
    String endtime;
    String amount;
    String description;
    String imageurl;
    String updatedtimestamp;
    String email;
    String givennameemail;
    String moblienumber;
    Double latitude;
    Double longitude;
    String removed;
    String upiid;
    String upiname;
    String rating;




    public TodayProvider(String id, String dname, String uname, String itemname, String starttime, String endtime, String amount, String description, String imageurl, String updatedtimestamp, String email, String givennameemail, String number, Double latitude, Double longitude, String removed, String upiid, String upiname, String rating) {
        this.id = id;
        this.dname = dname;
        this.uname = uname;
        this.itemname = itemname;
        this.starttime = starttime;
        this.endtime = endtime;
        this.amount = amount;
        this.description = description;
        this.imageurl = imageurl;
        this.updatedtimestamp = updatedtimestamp;
        this.email = email;
        this.givennameemail = givennameemail;
        this.moblienumber = number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.removed = "notremoved";
        this.upiid = upiid;
        this.upiname = upiname;
        this.rating = rating;


    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public String getMoblienumber() {
        return moblienumber;
    }

    public void setMoblienumber(String moblienumber) {
        this.moblienumber = moblienumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getUpiid() {
        return upiid;
    }

    public void setUpiid(String upiid) {
        this.upiid = upiid;
    }

    public String getUpiname() {
        return upiname;
    }

    public void setUpiname(String upiname) {
        this.upiname = upiname;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getMobileprovider() {
        return moblienumber;
    }

    public void setMobileprovider(String mobileprovider) {
        this.moblienumber = mobileprovider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivennameemail() {
        return givennameemail;
    }

    public void setGivennameemail(String givennameemail) {
        this.givennameemail = givennameemail;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getUpdatedtimestamp() {
        return updatedtimestamp;
    }

    public void setUpdatedtimestamp(String updatedtimestamp) {
        this.updatedtimestamp = updatedtimestamp;
    }

    public TodayProvider(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDname() {
        return dname;
    }

    public String getUname() {
        return uname;
    }

    public String getItemname() {
        return itemname;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
