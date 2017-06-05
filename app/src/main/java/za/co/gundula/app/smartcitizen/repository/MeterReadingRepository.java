package za.co.gundula.app.smartcitizen.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import za.co.gundula.app.smartcitizen.db.SmartCitizenDb;
import za.co.gundula.app.smartcitizen.entity.MeterReading;

/**
 * Created by kgundula on 2017/05/22.
 */

public interface MeterReadingRepository {

    Completable addMeterReading(MeterReading meterReading);

    LiveData<List<MeterReading>> getMeterReading(String accountNumber);

    Completable deleteMeterReading(MeterReading meterReading);

}
