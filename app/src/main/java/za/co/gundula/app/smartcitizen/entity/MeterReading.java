package za.co.gundula.app.smartcitizen.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.time.LocalDateTime;

import static za.co.gundula.app.smartcitizen.entity.MeterReading.TABLE_NAME;

/**
 * Created by kgundula on 2017/05/22.
 */

@Entity(tableName = TABLE_NAME)
public class MeterReading {

    public static final String TABLE_NAME = "meter_reading";
    public static final String METER_READING_DATE = "meter_reading_date";
    public static final String METER_ACCOUNT_NUMBER = "meter_account_number";

    @PrimaryKey(autoGenerate = true)
    private int meter_id;
    private String meter_electricity;
    private String meter_water;
    @ColumnInfo(name = METER_ACCOUNT_NUMBER)
    private String meter_account_number;
    @ColumnInfo(name = METER_READING_DATE)
    private String date;


    @Ignore
    public MeterReading() {
    }

    public MeterReading(int meter_id, String meter_electricity, String meter_water, String meter_account_number, String date) {
        this.meter_id = meter_id;
        this.meter_electricity = meter_electricity;
        this.meter_water = meter_water;
        this.meter_account_number = meter_account_number;
        this.date = date;
    }

    public int getMeter_id() {
        return meter_id;
    }

    public String getMeter_electricity() {
        return meter_electricity;
    }

    public String getMeter_water() {
        return meter_water;
    }

    public String getMeter_account_number() {
        return meter_account_number;
    }

    public String getDate() {
        return date;
    }


}
