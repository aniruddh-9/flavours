package com.example.flavours;

public class Order {

    String clientemail;
    String clientname;
    String clientnumber;
    String completedorder;
    String id;
    String itemcost;
    String itemsdescription;
    String itemendtime;
    String itemimageurl;
    String itemname;
    String itemproviderdisplayname;
    String itemprovideremail;
    String itemprovidername;
    String itemproviderphonenumber;
    String itemquantity;
    String itemstarttime;
    Double latitude;
    Double longitude;
    String ordersubmitted;
    String preparationstatus;
    String provideraddress;
    String provideruname;
    String statusacceptrreject;
    String timeleftorcompleted;
    String upiidclient;
    String upinameclient;


    public Order(String clientemail, String clientname, String clientnumber, String completedorder, String id, String itemcost, String itemsdescription, String itemendtime, String itemimageurl, String itemname, String itemproviderdisplayname, String itemprovideremail, String itemprovidername, String itemproviderphonenumber, String itemquantity, String itemstarttime, Double latitude, Double longitude, String ordersubmitted, String preparationstatus, String provideraddress, String provideruname, String statusacceptrreject, String timeleftorcompleted, String upiidclient, String upinameclient) {
        this.clientemail = clientemail;
        this.clientname = clientname;
        this.clientnumber = clientnumber;
        this.completedorder = completedorder;
        this.id = id;
        this.itemcost = itemcost;
        this.itemsdescription = itemsdescription;
        this.itemendtime = itemendtime;
        this.itemimageurl = itemimageurl;
        this.itemname = itemname;
        this.itemproviderdisplayname = itemproviderdisplayname;
        this.itemprovideremail = itemprovideremail;
        this.itemprovidername = itemprovidername;
        this.itemproviderphonenumber = itemproviderphonenumber;
        this.itemquantity = itemquantity;
        this.itemstarttime = itemstarttime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordersubmitted = ordersubmitted;
        this.preparationstatus = preparationstatus;
        this.provideraddress = provideraddress;
        this.provideruname = provideruname;
        this.statusacceptrreject = statusacceptrreject;
        this.timeleftorcompleted = timeleftorcompleted;
        this.upiidclient = upiidclient;
        this.upinameclient = upinameclient;
    }

    public Order(){

    }

    public String getClientemail() {
        return clientemail;
    }

    public void setClientemail(String clientemail) {
        this.clientemail = clientemail;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientnumber() {
        return clientnumber;
    }

    public void setClientnumber(String clientnumber) {
        this.clientnumber = clientnumber;
    }

    public String getCompletedorder() {
        return completedorder;
    }

    public void setCompletedorder(String completedorder) {
        this.completedorder = completedorder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemcost() {
        return itemcost;
    }

    public void setItemcost(String itemcost) {
        this.itemcost = itemcost;
    }

    public String getItemsdescription() {
        return itemsdescription;
    }

    public void setItemsdescription(String itemsdescription) {
        this.itemsdescription = itemsdescription;
    }

    public String getItemendtime() {
        return itemendtime;
    }

    public void setItemendtime(String itemendtime) {
        this.itemendtime = itemendtime;
    }

    public String getItemimageurl() {
        return itemimageurl;
    }

    public void setItemimageurl(String itemimageurl) {
        this.itemimageurl = itemimageurl;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemproviderdisplayname() {
        return itemproviderdisplayname;
    }

    public void setItemproviderdisplayname(String itemproviderdisplayname) {
        this.itemproviderdisplayname = itemproviderdisplayname;
    }

    public String getItemprovideremail() {
        return itemprovideremail;
    }

    public void setItemprovideremail(String itemprovideremail) {
        this.itemprovideremail = itemprovideremail;
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

    public String getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(String itemquantity) {
        this.itemquantity = itemquantity;
    }

    public String getItemstarttime() {
        return itemstarttime;
    }

    public void setItemstarttime(String itemstarttime) {
        this.itemstarttime = itemstarttime;
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

    public String getOrdersubmitted() {
        return ordersubmitted;
    }

    public void setOrdersubmitted(String ordersubmitted) {
        this.ordersubmitted = ordersubmitted;
    }

    public String getPreparationstatus() {
        return preparationstatus;
    }

    public void setPreparationstatus(String preparationstatus) {
        this.preparationstatus = preparationstatus;
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

    public String getStatusacceptrreject() {
        return statusacceptrreject;
    }

    public void setStatusacceptrreject(String statusacceptrreject) {
        this.statusacceptrreject = statusacceptrreject;
    }

    public String getTimeleftorcompleted() {
        return timeleftorcompleted;
    }

    public void setTimeleftorcompleted(String timeleftorcompleted) {
        this.timeleftorcompleted = timeleftorcompleted;
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
}
