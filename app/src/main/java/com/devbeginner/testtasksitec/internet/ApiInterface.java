package com.devbeginner.testtasksitec.internet;

import com.devbeginner.testtasksitec.model.response.ResultResponse;
import com.devbeginner.testtasksitec.model.response.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("{IMEI}/form/users")
    Call<UsersResponse> getUserList(@Path("IMEI") String imei);

    @GET("{IMEI}/authentication/?copyFromDevice=false&nfc=''")
    Call<ResultResponse> getResults(@Path("IMEI") String imei, @Query("uid") String uid, @Query("pass") String pass, @Query("copyFromDevice") Boolean copyFromDevice, @Query("nfc") String nfc);

}
