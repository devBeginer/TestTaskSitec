package com.devbeginner.testtasksitec;

/*import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;*/

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devbeginner.testtasksitec.model.db.ReceivedCodes;

import java.util.List;
import java.util.UUID;


@Dao
public interface ResultDao {
    @Insert(/*onConflict = OnConflictStrategy.IGNORE*/)
    void insert(ReceivedCodes result);

    @Update
    void update(ReceivedCodes result);

    @Query("SELECT * FROM ReceivedCodes")
    LiveData<List<ReceivedCodes>> getAll();

    @Query("SELECT * FROM ReceivedCodes WHERE user == (:uuid)")
    LiveData<List<ReceivedCodes>> getByUser(UUID uuid);

    @Delete
    void  delete(ReceivedCodes result);
}
