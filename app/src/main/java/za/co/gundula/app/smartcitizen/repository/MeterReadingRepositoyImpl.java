package za.co.gundula.app.smartcitizen.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import za.co.gundula.app.smartcitizen.db.SmartCitizenDb;
import za.co.gundula.app.smartcitizen.entity.MeterReading;

/**
 * Created by kgundula on 2017/05/23.
 */

public class MeterReadingRepositoyImpl implements MeterReadingRepository {

    @Inject
    SmartCitizenDb smartCitizenDb;

    public MeterReadingRepositoyImpl(SmartCitizenDb smartCitizenDb) {
        this.smartCitizenDb = smartCitizenDb;
    }

    @Override
    public LiveData<List<MeterReading>> getMeterReading(String accountNumber) {
        return smartCitizenDb.meterReadingDao().getMeterReading(accountNumber);
    }

    @Override
    public Completable addMeterReading(final MeterReading meterReading) {
        return Completable.fromAction(() -> smartCitizenDb.meterReadingDao().addMeterReading(meterReading));
    }

    @Override
    public Completable deleteMeterReading(final MeterReading meterReading) {
        return Completable.fromAction(() -> smartCitizenDb.meterReadingDao().deleteMeterReading(meterReading));
    }


}
