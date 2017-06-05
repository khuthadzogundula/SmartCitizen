package za.co.gundula.app.smartcitizen.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import za.co.gundula.app.smartcitizen.dao.MeterReadingDao;
import za.co.gundula.app.smartcitizen.dao.PropertyDao;
import za.co.gundula.app.smartcitizen.dao.UserDao;
import za.co.gundula.app.smartcitizen.entity.MeterReading;
import za.co.gundula.app.smartcitizen.entity.Property;
import za.co.gundula.app.smartcitizen.entity.User;

/**
 * Created by kgundula on 2017/05/22.
 */


@Database(entities = {User.class,Property.class, MeterReading.class}, version = 1, exportSchema = false)
public abstract class SmartCitizenDb extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PropertyDao propertyDao();
    public abstract MeterReadingDao meterReadingDao();

}
