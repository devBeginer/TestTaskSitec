package com.devbeginner.testtasksitec.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.devbeginner.testtasksitec.Repository;
import com.devbeginner.testtasksitec.model.db.ReceivedCodes;

import java.util.List;
import java.util.UUID;

public class SecondViewModel extends ViewModel {

    private Repository repository;
    public SecondViewModel(Repository repository){
        this.repository = repository;
    }

    public LiveData<List<ReceivedCodes>> getCodes(UUID uuid) {
        return repository.getDBResults(uuid);
    }
}
