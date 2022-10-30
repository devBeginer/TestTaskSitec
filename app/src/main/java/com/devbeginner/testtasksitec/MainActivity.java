package com.devbeginner.testtasksitec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> tmpArray = new ArrayList<String>();
        tmpArray.add("tmp1");
        tmpArray.add("tmp2");
        tmpArray.add("tmp3");
        tmpArray.add("tmp4");
        tmpArray.add("tmp5");


        Spinner spinner = findViewById(R.id.spinner_users);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tmpArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button loginButton = findViewById(R.id.btn_login);

    }
}