package com.devbeginner.testtasksitec;

/*import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;*/

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.devbeginner.testtasksitec.model.ReceivedCodes;

@Database(entities = {ReceivedCodes.class}, version = 1)
@TypeConverters(ResultTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ResultDao resultDao();
}
