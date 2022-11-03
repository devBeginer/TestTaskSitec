package com.devbeginner.testtasksitec.model.db;

/*import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;*/

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class ReceivedCodes {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public int code;
    public UUID user;

    public ReceivedCodes(int code, UUID user){
        this.code = code;
        this.user = user;
    }

    public int getCode() {
        return code;
    }
}
