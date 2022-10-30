package com.devbeginner.testtasksitec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.devbeginner.testtasksitec.model.ResultResponse;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ArrayList<ResultResponse> list = new ArrayList<>();
        list.add(new ResultResponse(123));
        list.add(new ResultResponse(456));
        list.add(new ResultResponse(789));
        list.add(new ResultResponse(753));


        RecyclerView recyclerView = findViewById(R.id.rv_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ItemAdapter(list));

    }
}