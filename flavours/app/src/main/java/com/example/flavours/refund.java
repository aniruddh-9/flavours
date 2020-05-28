package com.example.flavours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class refund extends AppCompatActivity {
    String dname, itemname, uname, amount, upiidclient, upinameclient, clientname, orderid, note;
    TextView upiid, upiname, amountrefund, itemnamerefund, refundbutton;
    int refamt;
    final int UPI_PAYMENT = 0;
    DatabaseReference databaseprovider;
    Context context;


    @Override
    public void onBackPressed(){
        startActivity(new Intent(refund.this, afterLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);


        dname = getIntent().getStringExtra("dname");
        itemname = getIntent().getStringExtra("itemname");
        uname = getIntent().getStringExtra("uname");
        amount = getIntent().getStringExtra("amount");
        upiidclient = getIntent().getStringExtra("upiidclient");
        upinameclient = getIntent().getStringExtra("upinameclient");
        clientname = getIntent().getStringExtra("clientname");
        orderid = getIntent().getStringExtra("orderid");

        itemnamerefund = findViewById(R.id.itemname);
        upiid = findViewById(R.id.clientupiid);
        upiname = findViewById(R.id.clientupiname);
        amountrefund = findViewById(R.id.clientrefundamount);
        refundbutton = (Button) findViewById(R.id.refundbutton);


        itemnamerefund.setText("Itemname : "+itemname);
        upiid.setText("UPI ID of customer : "+upiidclient);
        upiname.setText("UPI NAME of customer : "+upinameclient);
        amountrefund.setText("REFUND AMOUNT : "+amount);

        refamt = Integer.parseInt(amount);
        note = "Refunding to "+ clientname+ " for "+ itemname+ " an amount of rs "+ refamt;


        refundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                payUsingUpi(Integer.toString(refamt), upiidclient, upinameclient, note);

            }
        });




    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(refund.this)) {
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
                Toast.makeText(refund.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
//                createorder();
                databaseprovider = FirebaseDatabase.getInstance().getReference("orders");
                databaseprovider.child(orderid).child("statusacceptrreject").setValue("rejected");

                System.out.println("transaction succesful and rejected");


                Intent intent = new Intent(refund.this, providerorders.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("username",uname);
                intent.putExtra("displayname",dname);
                startActivity(intent);
                Toast.makeText(context, "rejected", Toast.LENGTH_SHORT).show();


                databaseprovider.child(orderid).child("statusacceptrreject").setValue("entered the debugging section");





                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(refund.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(refund.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(refund.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
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

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(refund.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }
}
