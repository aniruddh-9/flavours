

public class completedorders
{


    public String itname;
    public String providername;
    public String providerdisplayname;
    public String amount;
    public String date;
    public String clientname;
    public String clientmobile;


    public completedorders(String itname, String providername, String providerdisplayname, String amount, String date, String clientname, String clientmobile) {
        this.itname = itname;
        this.providername = providername;
        this.providerdisplayname = providerdisplayname;
        this.amount = amount;
        this.date = date;
        this.clientname = clientname;
        this.clientmobile = clientmobile;
    }

    public String getItname() {
        return itname;
    }

    public void setItname(String itname) {
        this.itname = itname;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientmobile() {
        return clientmobile;
    }

    public void setClientmobile(String clientmobile) {
        this.clientmobile = clientmobile;
    }
}
