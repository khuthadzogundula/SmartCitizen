package za.co.gundula.app.smartcitizen.injection;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import za.co.gundula.app.smartcitizen.SmartCitizenApplication;
import za.co.gundula.app.smartcitizen.db.SmartCitizenDb;
import za.co.gundula.app.smartcitizen.repository.MeterReadingRepository;
import za.co.gundula.app.smartcitizen.repository.MeterReadingRepositoyImpl;
import za.co.gundula.app.smartcitizen.repository.PropertyRepository;
import za.co.gundula.app.smartcitizen.repository.PropertyRepositoryImpl;
import za.co.gundula.app.smartcitizen.repository.UserRepository;
import za.co.gundula.app.smartcitizen.repository.UserRepositoryImpl;

/**
 * Created by kgundula on 2017/05/31.
 */

@Module
public class SmartCitizenModule {

    private SmartCitizenApplication smartCitizenApplication;

    public SmartCitizenModule(SmartCitizenApplication smartCitizenApplication) {
        this.smartCitizenApplication = smartCitizenApplication;
    }

    @Provides
    Context applicationContext() {
        return smartCitizenApplication;
    }

    @Provides
    @Singleton
    MeterReadingRepository providesMeterReadingRepository(SmartCitizenDb smartCitizenDb) {
        return new MeterReadingRepositoyImpl(smartCitizenDb);
    }

    @Provides
    @Singleton
    UserRepository providesUserRepository(SmartCitizenDb smartCitizenDb) {
        return new UserRepositoryImpl(smartCitizenDb);
    }

    @Provides
    @Singleton
    PropertyRepository providesPropertyRepository(SmartCitizenDb smartCitizenDb) {
        return new PropertyRepositoryImpl(smartCitizenDb);
    }

    @Provides
    @Singleton
    SmartCitizenDb providesSmartCitizenDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), SmartCitizenDb.class, "smart_citizen_db").build();
    }
}
