package com.example.flavours;

import com.example.flavours.Notifications.MyResponse;
import com.example.flavours.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA853lFO8:APA91bHTwUiLzX4iGmKdFtl7M-oNnrm1gzE_yipNAgVAfQDcJRHllX4IbolYR07pAaTJbyE6tJjVOPF9Mrn07HvXAdVLjCzcNvelVTWREaA14wBzKzDffwwzU4OpRHd4ATMs9af9mK6i"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}