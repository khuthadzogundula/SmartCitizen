package za.co.gundula.app.smartcitizen.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import za.co.gundula.app.smartcitizen.entity.MeterReading;
import za.co.gundula.app.smartcitizen.entity.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by kgundula on 2017/05/23.
 */

@Dao
public interface MeterReadingDao {

    @Query("SELECT * FROM " + MeterReading.TABLE_NAME + " WHERE " + MeterReading.METER_ACCOUNT_NUMBER + " > :accountNumber")
    LiveData<List<MeterReading>> getMeterReading(String accountNumber);

    @Insert(onConflict = REPLACE)
    void addMeterReading(MeterReading meterReading);

    @Delete
    void deleteMeterReading(MeterReading meterReading);

    @Update(onConflict = REPLACE)
    void updateMeterReading(MeterReading meterReading);

}
