package com.devbeginner.testtasksitec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.devbeginner.testtasksitec.model.ReceivedCodes;
import com.devbeginner.testtasksitec.model.ResultResponse;

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

        ArrayList<ResultResponse> list = new ArrayList<>();



        RecyclerView recyclerView = findViewById(R.id.rv_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ItemAdapter(list));

        Repository repository = new Repository();
        LiveData<List<ReceivedCodes>> receivedCodesLiveData = repository.getDBResults(UUID.fromString(uuid));
        receivedCodesLiveData.observe(this, new Observer<List<ReceivedCodes>>() {
            @Override
            public void onChanged(List<ReceivedCodes> receivedCodes) {
                ArrayList<ReceivedCodes> resultList = (ArrayList<ReceivedCodes>) receivedCodes;

                for (int i = 0; i < resultList.size(); i++){
                    list.add(new ResultResponse(resultList.get(i).code));
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

    }
}