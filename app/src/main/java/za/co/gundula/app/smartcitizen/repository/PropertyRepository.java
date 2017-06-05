package za.co.gundula.app.smartcitizen.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import za.co.gundula.app.smartcitizen.entity.MeterReading;
import za.co.gundula.app.smartcitizen.entity.Property;

/**
 * Created by kgundula on 2017/05/22.
 */

public interface PropertyRepository {

    Completable addProperty(Property property);

    LiveData<List<Property>> getProperty(String propertyOwner);

    Completable deleteProperty(Property property);
}
