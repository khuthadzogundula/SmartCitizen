package za.co.gundula.app.smartcitizen;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import za.co.gundula.app.smartcitizen.db.SmartCitizenDb;
import za.co.gundula.app.smartcitizen.injection.DaggerSmartCitizenComponent;
import za.co.gundula.app.smartcitizen.injection.SmartCitizenComponent;
import za.co.gundula.app.smartcitizen.injection.SmartCitizenModule;

/**
 * Created by kgundula on 2017/05/09.
 */

public class SmartCitizenApplication extends Application {

    public static boolean isTablet = false;
    public static String FILES_DIR;

    private final SmartCitizenComponent smartCitizenComponent = createSmartCitizenComponent();


    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        isTablet = getResources().getBoolean(R.bool.is_tablet_mode);
        FILES_DIR = getFilesDir().getPath();

        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree());

            //Enable StrictMode
            /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
            */


        } else {
            Fabric.with(this, new Crashlytics());
        }

        SmartCitizenDb db = Room.databaseBuilder(getApplicationContext(),
                SmartCitizenDb.class, "database-name").build();

    }


    protected SmartCitizenComponent createSmartCitizenComponent() {
        return DaggerSmartCitizenComponent.builder()
                .smartCitizenModule(new SmartCitizenModule(this))
                .build();
    }

    public SmartCitizenComponent getSmartCitizenComponent() {
        return smartCitizenComponent;
    }

}
