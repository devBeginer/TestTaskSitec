package com.devbeginner.testtasksitec.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import com.devbeginner.testtasksitec.internet.OnError;
import com.devbeginner.testtasksitec.R;
import com.devbeginner.testtasksitec.model.User;
import com.devbeginner.testtasksitec.viewmodel.MainViewModel;

import java.util.ArrayList;
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


        MainViewModel mainViewModel = new MainViewModel();
        mainViewModel.getUsersList(imei).observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                tmpArray.addAll(strings);
                adapter.notifyDataSetChanged();
            }
        });
        mainViewModel.getCurrentUid().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                spinner.setSelection(integer);
                adapter.notifyDataSetChanged();
            }
        });

        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(view -> {
            String password = passwordEditText.getText().toString();
            mainViewModel.getResults(selectedUser, password, imei, new OnError() {

                @Override
                public void onError(String error) {
                    alertAlert("Ошибка", "Ошибка входа: " + error);
                }
            }).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String string) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("uuid", string);
                    startActivity(intent);
                }
            });
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

    private String getImei(){
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestReadPhoneStatePermission();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                return String.valueOf(UUID.randomUUID());
            }else{
                return "111111111111111";
            }
        } else {
            alertAlert("Permission Request", "Android 10 (уровень API 29) добавляет ограничения для не сбрасываемых идентификаторов, которые включают как IMEI, так и серийный номер. Ваше приложение должно быть приложением владельца устройства или профиля, иметь специальные разрешения оператора или иметь привилегированное разрешение READ_PRIVILEGED_PHONE_STATE, чтобы получить доступ к этим идентификаторам. Будет применен IMEI 111111111111111");
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
                    .setMessage("Разрешить чтение данных о телефоне? IMEI применяется для авторизации устройства на сервере.")
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
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
                alertAlert("Permission Request", "Разрешение получено.");
                alertAlert("Permission Request", "Android 10 (уровень API 29) добавляет ограничения для не сбрасываемых идентификаторов, которые включают как IMEI, так и серийный номер. Ваше приложение должно быть приложением владельца устройства или профиля, иметь специальные разрешения оператора или иметь привилегированное разрешение READ_PRIVILEGED_PHONE_STATE, чтобы получить доступ к этим идентификаторам. Будет применен IMEI 111111111111111");
            } else {
                alertAlert("Permission Request", "Разрешение на чтение данных о телефоне не получено, будет применен случайный UUID");
            }
        }
    }

    private void alertAlert(String title, String msg) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(/*"Permission Request"*/title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}