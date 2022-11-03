package com.devbeginner.testtasksitec.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devbeginner.testtasksitec.internet.OnError;
import com.devbeginner.testtasksitec.internet.OnResponse;
import com.devbeginner.testtasksitec.Repository;
import com.devbeginner.testtasksitec.model.db.ReceivedCodes;
import com.devbeginner.testtasksitec.model.response.ResultResponse;
import com.devbeginner.testtasksitec.model.User;
import com.devbeginner.testtasksitec.model.response.Users;
import com.devbeginner.testtasksitec.model.response.UsersResponse;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MainViewModel extends ViewModel {
    private Repository repository;

    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    private ArrayList<User> usersList;


    private MutableLiveData<Integer> currentUid = new MutableLiveData<Integer>();

    public LiveData<Integer> getCurrentUid() {
        return currentUid;
    }

    public LiveData<ArrayList<String>> getUsersList(String imei) {
        MutableLiveData<ArrayList<String>> loadUsers = new MutableLiveData<>();

        repository.getUsers(imei, new OnResponse<UsersResponse>() {
            @Override
            public void onSuccess(UsersResponse data) {
                ArrayList<User> list = data.getUsers().getListUsers();
                ArrayList<String> tmpArray = new ArrayList();

                int a = 0;
                while (a < list.size()) {
                    tmpArray.add(list.get(a).getUser());
                    a++;
                }
                loadUsers.postValue(tmpArray);

                currentUid.postValue(getSelectedUser(data.getUsers()));
            }

            @Override
            public void onFailure(String error) {

            }
        });
        return loadUsers;
    }

    private int getSelectedUser(Users users) {
        String currentUid = users.getCurrentUid();
        usersList = users.getListUsers();
        int i = 0;

        while (i < usersList.size()) {
            if (Objects.equals(usersList.get(i).getUid(), currentUid)) {
                return i;
            } else {
                i++;
            }
        }
        return 0;
    }

    public LiveData<String> getResults(int selected, String password, String imei, OnError callback) {
        MutableLiveData<String> loadResults = new MutableLiveData<>();

        repository.getResult(usersList.get(selected).getUid(), password, false, "", imei, new OnResponse<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse data) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        repository.insertResults(new ReceivedCodes(data.getCode(), UUID.fromString(usersList.get(selected).getUid())));

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void unused) {
                        loadResults.postValue(usersList.get(selected).getUid());
                    }
                }.execute();
            }

            @Override
            public void onFailure(String error) {
                callback.onError(error);
            }
        });
        return loadResults;
    }
}
