package za.co.gundula.app.smartcitizen.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import za.co.gundula.app.smartcitizen.entity.MeterReading;
import za.co.gundula.app.smartcitizen.entity.User;

/**
 * Created by kgundula on 2017/05/22.
 */

public interface UserRepository {


    Completable addUser(User user);

    LiveData<List<User>> getUser(String userId);

    Completable deleteUser(User user);

}
