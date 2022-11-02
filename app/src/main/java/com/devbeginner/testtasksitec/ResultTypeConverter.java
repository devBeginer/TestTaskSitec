package com.devbeginner.testtasksitec;

/*import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;*/

import androidx.room.TypeConverter;

import java.util.UUID;

public class ResultTypeConverter {
    @TypeConverter
    public UUID toUUID(String uuid) {
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public String fromUUID(UUID uuid) {
        return uuid.toString();
    }
}
