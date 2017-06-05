package za.co.gundula.app.smartcitizen.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import za.co.gundula.app.smartcitizen.db.SmartCitizenDb;
import za.co.gundula.app.smartcitizen.entity.Property;

/**
 * Created by kgundula on 2017/05/31.
 */

public class PropertyRepositoryImpl implements PropertyRepository {

    @Inject
    SmartCitizenDb smartCitizenDb;

    public PropertyRepositoryImpl(SmartCitizenDb smartCitizenDb) {
        this.smartCitizenDb = smartCitizenDb;
    }

    @Override
    public Completable addProperty(Property property) {
        return  Completable.fromAction(() -> smartCitizenDb.propertyDao().addProperty(property));
    }

    @Override
    public LiveData<List<Property>> getProperty(String propertyOwner) {
        return smartCitizenDb.propertyDao().getProperty(propertyOwner);
    }

    @Override
    public Completable deleteProperty(Property property) {
        return Completable.fromAction(() -> smartCitizenDb.propertyDao().deleteProperty(property));
    }
}
