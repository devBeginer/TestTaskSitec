package com.devbeginner.testtasksitec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.devbeginner.testtasksitec.di.DI;
import com.devbeginner.testtasksitec.model.ReceivedCodes;
import com.devbeginner.testtasksitec.model.ResultResponse;
import com.devbeginner.testtasksitec.model.User;
import com.devbeginner.testtasksitec.model.Users;
import com.devbeginner.testtasksitec.model.UsersResponse;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    private int selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String imei = getImei();
        EditText passwordEditText = findViewById(R.id.et_password);


        ArrayList<String> tmpArray = new ArrayList<String>();
        ArrayList<User> userArray = new ArrayList<User>();

        Spinner spinner = findViewById(R.id.spinner_users);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tmpArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ApiInterface apiInterface = DI.getRetrofitInstance().create(ApiInterface.class);
        Repository repository = new Repository();
        LiveData<UsersResponse> usersResponseLiveData = repository.getUsers(/*"111111111111111"*/ imei);
        usersResponseLiveData.observe(this, new Observer<UsersResponse>() {
            @Override
            public void onChanged(UsersResponse usersResponse) {
                ArrayList<User> list = usersResponse.getUsers().getListUsers();
                userArray.addAll(list);
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
            String uuid = userArray.get(selectedUser).getUid();
            String password = passwordEditText.getText().toString();
            LiveData<ResultResponse> resultResponseLiveData =
                    repository.getResult(uuid, password, false, "", "111111111111111", ()->{
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Ошибка")
                                .setMessage("Ошибка входа")
                                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                        return null;
                    });
            resultResponseLiveData.observe(this, new Observer<ResultResponse>() {
                @Override
                public void onChanged(ResultResponse resultResponse) {
                    System.out.println("\n\n\n\n\n"+ Integer.toString(resultResponse.getCode())+"\n\n\n\n\n\n");

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            repository.insertResults(new ReceivedCodes(resultResponse.getCode(), UUID.fromString(uuid)));

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("uuid", uuid);
                            startActivity(intent);                        }
                    }.execute();
                }
            });
            /*Intent intent = new Intent(MainActivity.this, SecondActivity.class);

            startActivity(intent);*/
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUser = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedUser = 0;
            }
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

    private String getImei(){
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestReadPhoneStatePermission();
            return String.valueOf(UUID.randomUUID());
        } else {
            alertAlert("Android 10 (уровень API 29) добавляет ограничения для не сбрасываемых идентификаторов, которые включают как IMEI, так и серийный номер. Ваше приложение должно быть приложением владельца устройства или профиля, иметь специальные разрешения оператора или иметь привилегированное разрешение READ_PRIVILEGED_PHONE_STATE, чтобы получить доступ к этим идентификаторам. Будет применен IMEI 111111111111111");
            /*String deviceUniqueIdentifier = null;
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    deviceUniqueIdentifier = tm.getImei();
                }else {
                    deviceUniqueIdentifier =tm.getDeviceId();
                }
            }
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
                deviceUniqueIdentifier = String.valueOf(UUID.randomUUID());
            }
            System.out.println(deviceUniqueIdentifier);
            return deviceUniqueIdentifier;*/
            return "111111111111111";
        }
    }


    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permission Request")
                    .setMessage("Разрешить чтение данных о телефоне?")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                alertAlert("Разрешение получено");
                alertAlert("Android 10 (уровень API 29) добавляет ограничения для не сбрасываемых идентификаторов, которые включают как IMEI, так и серийный номер. Ваше приложение должно быть приложением владельца устройства или профиля, иметь специальные разрешения оператора или иметь привилегированное разрешение READ_PRIVILEGED_PHONE_STATE, чтобы получить доступ к этим идентификаторам. Будет применен IMEI 111111111111111");
            } else {
                alertAlert("Разрешение на чтение данных о телефоне не получено, будет применен случайный UUID");
            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Permission Request")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}