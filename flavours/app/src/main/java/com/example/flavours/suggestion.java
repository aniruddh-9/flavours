package com.example.flavours;

public class suggestion {

    public String id;
    public String itemname;
    public String providername;
    public String providerdisplayname;
    public String suggestion;

    public suggestion(String id, String itemname, String providername, String providerdisplayname, String suggestion) {
        this.id = id;
        this.itemname = itemname;
        this.providername = providername;
        this.providerdisplayname = providerdisplayname;
        this.suggestion = suggestion;
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

    public String getProvidername() {
        return providername;
    }

    public void setProvidername(String providername) {
        this.providername = providername;
    }

    public String getProviderdisplayname() {
        return providerdisplayname;
    }

    public void setProviderdisplayname(String providerdisplayname) {
        this.providerdisplayname = providerdisplayname;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
