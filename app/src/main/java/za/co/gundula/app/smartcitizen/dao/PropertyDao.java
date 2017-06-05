package za.co.gundula.app.smartcitizen.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import za.co.gundula.app.smartcitizen.entity.MeterReading;
import za.co.gundula.app.smartcitizen.entity.Property;
import za.co.gundula.app.smartcitizen.entity.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by kgundula on 2017/05/23.
 */

@Dao
public interface PropertyDao {

    @Query("SELECT * FROM " + Property.TABLE_NAME + " WHERE " + Property.PROPERTY_OWNER + " > :propertyOwner")
    LiveData<List<Property>> getProperty(String propertyOwner);

    @Insert(onConflict = REPLACE)
    void addProperty(Property property);

    @Delete
    void deleteProperty(Property property);

    @Update(onConflict = REPLACE)
    void updateMProperty(Property property);

}
