package com.devbeginner.testtasksitec;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devbeginner.testtasksitec.internet.ApiInterface;
import com.devbeginner.testtasksitec.internet.OnResponse;
import com.devbeginner.testtasksitec.model.db.ReceivedCodes;
import com.devbeginner.testtasksitec.model.response.ResultResponse;
import com.devbeginner.testtasksitec.model.response.UsersResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {
    private ApiInterface apiInterface;
    private ResultDao resultDao;

    public Repository(Retrofit retrofit, ResultDao resultDao) {
        apiInterface = retrofit.create(ApiInterface.class);
        //apiInterface = DI.getRetrofitInstance().create(ApiInterface.class);

        this.resultDao = resultDao;
        //resultDao = DI.getDatabaseInstance().resultDao();
    }

    public LiveData<List<ReceivedCodes>> getDBResults(UUID user){
        return resultDao.getByUser(user);
    }

    public void insertResults(ReceivedCodes result){
        resultDao.insert(result);
    }


    public LiveData<UsersResponse> getUsers(String imei) {
        MutableLiveData<UsersResponse> responseLiveData = new MutableLiveData<>();

        apiInterface.getUserList( imei)
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


    public void getUsers(String imei, OnResponse<UsersResponse> listener) {

        apiInterface.getUserList(imei)
                .enqueue(new Callback<UsersResponse>() {
                    @Override
                    public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                        if (response.isSuccessful()) {

                            listener.onSuccess(response.body());

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
                                listener.onFailure(String.valueOf(response.code())+" code: "+errorBody.string());
                                System.out.println(errorBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UsersResponse> call, Throwable t) {
                        System.out.println("Request Failure");
                        listener.onFailure("Request Failure");
                    }
                });
    }


    public void getResult(
            String uid,
            String pass,
            Boolean copyFromDevice,
            String nfc,
            String imei,
            OnResponse<ResultResponse> listener) {

        apiInterface.getResults(imei,
                        uid,
                        pass,
                        copyFromDevice,
                        nfc)
                .enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        if (response.isSuccessful()) {
                            System.out.println(response.code());
                            listener.onSuccess(response.body());
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
                                listener.onFailure(errorBody.string());
                                System.out.println(errorBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        listener.onFailure("Request Failure");
                        System.out.println("Request Failure");
                    }
                });

    }
}
