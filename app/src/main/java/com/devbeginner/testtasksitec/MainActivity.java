package com.devbeginner.testtasksitec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.devbeginner.testtasksitec.di.DI;
import com.devbeginner.testtasksitec.model.ResultResponse;
import com.devbeginner.testtasksitec.model.User;
import com.devbeginner.testtasksitec.model.Users;
import com.devbeginner.testtasksitec.model.UsersResponse;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> tmpArray = new ArrayList<String>();

        Spinner spinner = findViewById(R.id.spinner_users);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tmpArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ApiInterface apiInterface = DI.getRetrofitInstance().create(ApiInterface.class);
        Repository repository = new Repository();
        LiveData<UsersResponse> usersResponseLiveData = repository.getUsers();
        usersResponseLiveData.observe(this, new Observer<UsersResponse>() {
            @Override
            public void onChanged(UsersResponse usersResponse) {
                ArrayList<User> list = usersResponse.getUsers().getListUsers();
                int a = 0;
                while (a < list.size()) {
                    tmpArray.add(list.get(a).getUser());
                    a++;
                }

                if (usersResponse.getUsers().getCurrentUid() != null) {
                    spinner.setSelection(getSelectedUser(usersResponse.getUsers()));

                }
                adapter.notifyDataSetChanged();
            }
        });

        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(view -> {
            LiveData<ResultResponse> resultResponseLiveData =
                    repository.getResult("fa1cb834-d744-11ec-ab52-000c29601d6b", "zxc", false, "");

            resultResponseLiveData.observe(this, new Observer<ResultResponse>() {
                @Override
                public void onChanged(ResultResponse resultResponse) {
                    System.out.println("\n\n\n\n\n"+ resultResponse.getCode().toString()+"\n\n\n\n\n\n");
                }
            });
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }

    private int getSelectedUser(Users users) {
        String currentUid = users.getCurrentUid();
        ArrayList<User> list = users.getListUsers();
        int i = 0;

        while (i < list.size()) {
            if (Objects.equals(list.get(i).getUid(), currentUid)) {
                return i;
            } else {
                i++;
            }
        }
        return 0;
    }
}