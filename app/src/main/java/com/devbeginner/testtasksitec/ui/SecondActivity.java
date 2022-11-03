package com.devbeginner.testtasksitec.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.devbeginner.testtasksitec.ItemAdapter;
import com.devbeginner.testtasksitec.R;
import com.devbeginner.testtasksitec.di.Singleton;
import com.devbeginner.testtasksitec.model.db.ReceivedCodes;
import com.devbeginner.testtasksitec.viewmodel.SecondViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");

        ArrayList<ReceivedCodes> list = new ArrayList<>();



        RecyclerView recyclerView = findViewById(R.id.rv_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ItemAdapter(list));

        SecondViewModel secondViewModel = Singleton.getSecondViewModelInstance();

        secondViewModel.getCodes(UUID.fromString(uuid)).observe(this, new Observer<List<ReceivedCodes>>() {
            @Override
            public void onChanged(List<ReceivedCodes> receivedCodes) {
                ArrayList<ReceivedCodes> resultList = (ArrayList<ReceivedCodes>) receivedCodes;

                list.addAll(resultList);


                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

    }
}