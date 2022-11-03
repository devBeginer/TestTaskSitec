package com.devbeginner.testtasksitec;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.devbeginner.testtasksitec.model.ResultTypeConverter;
import com.devbeginner.testtasksitec.model.db.ReceivedCodes;

@Database(entities = {ReceivedCodes.class}, version = 1)
@TypeConverters(ResultTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ResultDao resultDao();
}
