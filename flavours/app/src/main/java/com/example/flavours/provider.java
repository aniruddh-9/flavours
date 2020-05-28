package com.example.flavours;

import androidx.annotation.NonNull;

public class provider {


    String providerid;
    String providername;
    String displayname;
    String username;
    String provideraddress;
    String providernumber;
    String safetynumber;
    String email;
    String givennameemail;
    //int rating = -1;
    double latitude;
    double longitude;
    String ratingprovider;
    String numberofratings;

    public provider(){

    }

    public provider(String providerid, String providername, String displayname, String username, String provideraddress, String providernumber, String safetynumber, String email, String givennameemail, double latitude, double longitude, String rating, String numberrating) {
        this.providerid = providerid;
        this.providername = providername;
        this.displayname = displayname;
        this.username = username;
        this.provideraddress = provideraddress;
        this.providernumber = providernumber;
        this.safetynumber = safetynumber;
        this.email = email;
        this.givennameemail = givennameemail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ratingprovider = "4";
        this.numberofratings = "1";
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getRatingprovider() {
        return ratingprovider;
    }

    public void setRatingprovider(String ratingprovider) {
        this.ratingprovider = ratingprovider;
    }

    public String getNumberofratings() {
        return numberofratings;
    }

    public void setNumberofratings(String numberofratings) {
        this.numberofratings = numberofratings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getProvideraddress() {
        return provideraddress;
    }

    public String getProviderid() {
        return providerid;
    }

    public String getProvidername() {
        return providername;
    }


    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

    public void setProvidername(String providername) {
        this.providername = providername;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setProvideraddress(String provideraddress) {
        this.provideraddress = provideraddress;
    }

    public void setProvidernumber(String providernumber) {
        this.providernumber = providernumber;
    }

    public void setSafetynumber(String safetynumber) {
        this.safetynumber = safetynumber;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvidernumber() {
        return providernumber;
    }

    public String getSafetynumber() {
        return safetynumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return ratingprovider+" "+numberofratings;
    }
}
