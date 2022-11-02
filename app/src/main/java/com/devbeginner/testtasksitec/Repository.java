package com.devbeginner.testtasksitec;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devbeginner.testtasksitec.di.DI;
import com.devbeginner.testtasksitec.model.ReceivedCodes;
import com.devbeginner.testtasksitec.model.ResultResponse;
import com.devbeginner.testtasksitec.model.UsersResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ApiInterface apiInterface;
    private ResultDao resultDao;

    public Repository() {
        apiInterface = DI.getRetrofitInstance().create(ApiInterface.class);

        resultDao = DI.getDatabaseInstance().resultDao();
    }

    public LiveData<List<ReceivedCodes>> getDBResults(UUID user){
        //return resultDao.getAll();
        return resultDao.getByUser(user);
    }

    public void insertResults(ReceivedCodes result){
        resultDao.insert(result);
    }


    public LiveData<UsersResponse> getUsers(String imei) {
        MutableLiveData<UsersResponse> responseLiveData = new MutableLiveData<>();

        apiInterface.getUserList(/*"111111111111111"*/ imei)
                .enqueue(new Callback<UsersResponse>() {
                    @Override
                    public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                        if (response.isSuccessful()) {

                            responseLiveData.setValue(response.body());

                        } else {
                            switch (response.code()) {
                                case 404:
                                    // страница не найдена.
                                    break;
                                case 500:
                                    // ошибка на сервере.
                                    break;
                            }

                            ResponseBody errorBody = response.errorBody();
                            try {
                                System.out.println(errorBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UsersResponse> call, Throwable t) {
                        System.out.println("Request Failure");
                    }
                });

        return responseLiveData;
    }


    public LiveData<ResultResponse> getResult(
            String uid,
            String pass,
            Boolean copyFromDevice,
            String nfc,
            String imei,
            OnErrorInterface onError) {
        MutableLiveData<ResultResponse> responseLiveData = new MutableLiveData<>();

        apiInterface.getResults(
                /*"111111111111111"*/imei,
                        uid,
                        pass,
                        copyFromDevice,
                        nfc)
                .enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        if (response.isSuccessful()) {
                            System.out.println(response.code());
                            responseLiveData.setValue(response.body());
                        } else {
                            onError.onError();
                            switch (response.code()) {
                                case 404:
                                    // страница не найдена.
                                    break;
                                case 500:
                                    // ошибка на сервере.
                                    break;
                            }

                            ResponseBody errorBody = response.errorBody();
                            try {
                                System.out.println(errorBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        System.out.println("Request Failure");
                    }
                });

        return responseLiveData;
    }
}
