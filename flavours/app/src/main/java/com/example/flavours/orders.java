package com.example.flavours;

public class orders {
    public String id;
    public String itemname;
    public String itemcost;
    public String itemdescription;
    public String itemprovidername;
    public String itemproviderphonenumber;
    public String itemstarttime;
    public String itemendtime;
    public String itemprovideremail;
    public String itemproviderdisplayname;
    public String itemquantity;
    public String provideraddress;
    public String provideruname;
    public String clientnumber;
    public String clientname;
    public String clientemail;
    public String itemimageurl;
    public String statusacceptrreject;
    public String timeleftorcompleted;
    public String preperationstatus;
    Double latitude;
    Double longitude;
    public String upiidclient;
    public String upinameclient;
    public String ordersubmitted;
    public String completedorder;



    public String getCompletedorder() {
        return completedorder;
    }

    public void setCompletedorder(String completedorder) {
        this.completedorder = completedorder;
    }

    public String getUpiidclient() {
        return upiidclient;
    }

    public void setUpiidclient(String upiidclient) {
        this.upiidclient = upiidclient;
    }

    public String getUpinameclient() {
        return upinameclient;
    }

    public void setUpinameclient(String upinameclient) {
        this.upinameclient = upinameclient;
    }

    public String getOrdersubmitted() {
        return ordersubmitted;
    }

    public void setOrdersubmitted(String ordersubmitted) {
        this.ordersubmitted = ordersubmitted;
    }

    public orders(String id, String itemname, String itemcost, String itemdescription, String itemprovidername, String itemproviderphonenumber, String itemstarttime, String itemendtime, String itemprovideremail, String itemproviderdisplayname, String itemquantity, String provideraddress, String provideruname, String clientnumber, String clientname, String clientemail, String itemimageurl, String statusacceptrreject, String timeleftorcompleted, Double lat, Double lon, String preperationstatus, String upiidclient, String upinameclient, String ordersubmitted, String completedorder) {
        this.id = id;
        this.itemname = itemname;
        this.itemcost = itemcost;
        this.itemdescription = itemdescription;
        this.itemprovidername = itemprovidername;
        this.itemproviderphonenumber = itemproviderphonenumber;
        this.itemstarttime = itemstarttime;
        this.itemendtime = itemendtime;
        this.itemprovideremail = itemprovideremail;
        this.itemproviderdisplayname = itemproviderdisplayname;
        this.itemquantity = itemquantity;
        this.provideraddress = provideraddress;
        this.provideruname = provideruname;
        this.clientnumber = clientnumber;
        this.clientname = clientname;
        this.clientemail = clientemail;
        this.itemimageurl = itemimageurl;
        this.statusacceptrreject = "pending";
        this.timeleftorcompleted = "notprovided";
        this.latitude = lat;
        this.longitude  = lon;
        this.preperationstatus = "not yet accepted";
        this.upiidclient = upiidclient;
        this.upinameclient = upinameclient;
        this.ordersubmitted = ordersubmitted;
        this.completedorder = "no";
    }


    public orders() {
    }

    public String getTimeleftorcompleted() {
        return timeleftorcompleted;
    }

    public void setTimeleftorcompleted(String timeleftorcompleted) {
        this.timeleftorcompleted = timeleftorcompleted;
    }

    public String getPreperationstatus() {
        return preperationstatus;
    }

    public void setPreperationstatus(String preperationstatus) {
        this.preperationstatus = preperationstatus;
    }

    public Double getLatitude() {
        return latitude;
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

    public String getStatusacceptrreject() {
        return statusacceptrreject;
    }

    public void setStatusacceptrreject(String statusacceptrreject) {
        this.statusacceptrreject = statusacceptrreject;
    }

    public String getItemimageurl() {
        return itemimageurl;
    }

    public void setItemimageurl(String itemimageurl) {
        this.itemimageurl = itemimageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemcost() {
        return itemcost;
    }

    public void setItemcost(String itemcost) {
        this.itemcost = itemcost;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public String getItemprovidername() {
        return itemprovidername;
    }

    public void setItemprovidername(String itemprovidername) {
        this.itemprovidername = itemprovidername;
    }

    public String getItemproviderphonenumber() {
        return itemproviderphonenumber;
    }

    public void setItemproviderphonenumber(String itemproviderphonenumber) {
        this.itemproviderphonenumber = itemproviderphonenumber;
    }

    public String getItemstarttime() {
        return itemstarttime;
    }

    public void setItemstarttime(String itemstarttime) {
        this.itemstarttime = itemstarttime;
    }

    public String getItemendtime() {
        return itemendtime;
    }

    public void setItemendtime(String itemendtime) {
        this.itemendtime = itemendtime;
    }

    public String getItemprovideremail() {
        return itemprovideremail;
    }

    public void setItemprovideremail(String itemprovideremail) {
        this.itemprovideremail = itemprovideremail;
    }

    public String getItemproviderdisplayname() {
        return itemproviderdisplayname;
    }

    public void setItemproviderdisplayname(String itemproviderdisplayname) {
        this.itemproviderdisplayname = itemproviderdisplayname;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(String itemquantity) {
        this.itemquantity = itemquantity;
    }

    public String getProvideraddress() {
        return provideraddress;
    }

    public void setProvideraddress(String provideraddress) {
        this.provideraddress = provideraddress;
    }

    public String getProvideruname() {
        return provideruname;
    }

    public void setProvideruname(String provideruname) {
        this.provideruname = provideruname;
    }

    public String getClientnumber() {
        return clientnumber;
    }

    public void setClientnumber(String clientnumber) {
        this.clientnumber = clientnumber;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientemail() {
        return clientemail;
    }

    public void setClientemail(String clientemail) {
        this.clientemail = clientemail;
    }
}
