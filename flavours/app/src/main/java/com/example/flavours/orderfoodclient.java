package com.example.flavours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class orderfoodclient extends AppCompatActivity {



    EditText providername, price, itemname, address, quantity, mobilenumber,upiidpayment, upinamepayment, netamountpayment;
    Button createorder;
    String provname, pr, itmname, add, dname, uname, imageurl, mobnum, em, lat, lon, mobclient, description, sttime, entime;
    Double latitude, longitude;
    String quan="1";
    DatabaseReference databaseprovider;
    String upiid, upiname, note;
    final int UPI_PAYMENT = 0;
    int paymentquantity;
    String amount, mobileclient;
    EditText upiidclient, upinameclient;
    String upiidclientstore, upinameclientstore;


    @Override
    public void onBackPressed(){
        startActivity(new Intent(orderfoodclient.this, foodsearch.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderfoodclient);


        provname = getIntent().getStringExtra("uname");
        pr = getIntent().getStringExtra("amount");
        itmname = getIntent().getStringExtra("itemname");
        lat = getIntent().getStringExtra("latitude");
        latitude = Double.parseDouble(lat);
        lon = getIntent().getStringExtra("longitude");
        longitude = Double.parseDouble(lon);
        dname = getIntent().getStringExtra("dname");
        uname = getIntent().getStringExtra("uname");
        imageurl = getIntent().getStringExtra("imageurl");
        mobnum = getIntent().getStringExtra("mobilenumber");
        em = getIntent().getStringExtra("email");
        description = getIntent().getStringExtra("description");
        sttime = getIntent().getStringExtra("starttime");
        entime = getIntent().getStringExtra("endtime");
        upiid = getIntent().getStringExtra("upiid");
        upiname = getIntent().getStringExtra("upiname");


        databaseprovider = FirebaseDatabase.getInstance().getReference("orders");

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();

        System.out.println("location display"+ city +"  "+state+"   "+address1);

        upiidpayment = findViewById(R.id.upiidpayment);
        upinamepayment = findViewById(R.id.upinamepayment);
        providername = findViewById(R.id.providername);
        price = findViewById(R.id.price);
        itemname = findViewById(R.id.itemname);
        address = findViewById(R.id.address);
        quantity = findViewById(R.id.quantity);
        mobilenumber = findViewById(R.id.mobileclient);
        createorder = findViewById(R.id.createorder);
        upiidclient = findViewById(R.id.upiidclient);
        upinameclient = findViewById(R.id.clientupiname);



        upiidpayment.setText("UPI ID of the provider is "+upiid);
        upinamepayment.setText("UPI NAME of the provider is "+upiname);




        providername.setText("Provider Name : "+provname);
        price.setText("Price : "+pr);
        itemname.setText("Item Name : "+itmname);
        address.setText("address is "+ address1);




        createorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amount = quantity.getText().toString();
                mobileclient = mobilenumber.getText().toString();
                upiidclientstore = upiidclient.getText().toString();
                upinameclientstore = upinameclient.getText().toString();
                System.out.println("you have pressed the create order button"+" the value is "+amount+" and "+mobclient);
//                if(!amount.equals("")&& !mobileclient.equals("")) {



                    int amt = Integer.parseInt(amount);
                    int pric = Integer.parseInt(pr);
                    int total = amt*pric;
                    System.out.println("enterd the ordering section");

                    String note = "payment to "+dname+ " for "+itmname;
                    String name = upiname;
                    String upiId = upiid;

                    System.out.println("toal is " + total);
                    System.out.println("upiid is "+upiid);
                    System.out.println("upiname is "+upiname);
                    System.out.println(" note is "+note);

//                }
                payUsingUpi(Integer.toString(total), upiid, upiname, note);
//                createorder();
//                System.out.println("do something here");

            }
        });

    }
    public  void createorder(){
        String personName = null, personEmail = null;

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();

        }
        String uploadId = databaseprovider.push().getKey();
        orders o = new orders(uploadId, itmname, pr, description, uname, mobnum, sttime, entime, em, dname, quantity.getText().toString(), "address", uname, mobilenumber.getText().toString(), personName,personEmail,imageurl," ","",latitude,longitude,"",upiidclientstore,upinameclientstore,"no","no");
        databaseprovider.child(uploadId).setValue(o);
        Toast.makeText(orderfoodclient.this, "This is my Toast message!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(orderfoodclient.this, notifications.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("dname",dname);
        intent.putExtra("itemname",itmname);
        intent.putExtra("uname",uname);
        intent.putExtra("amount",pr);
        intent.putExtra("imageurl",imageurl);
        intent.putExtra("mobilenumber",mobnum);
        intent.putExtra("email",em);
        intent.putExtra("latitude",latitude.toString());
        intent.putExtra("longitude",longitude.toString());
        intent.putExtra("description",description);
        intent.putExtra("starttime",sttime);
        intent.putExtra("endtime",entime);
        intent.putExtra("mobileclient",mobclient);
        startActivity(intent);


    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(orderfoodclient.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String[] equalStr = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(orderfoodclient.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                createorder();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(orderfoodclient.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(orderfoodclient.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(orderfoodclient.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    void payUsingUpi(String amount, String upiId, String name, String note) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(orderfoodclient.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }
}
