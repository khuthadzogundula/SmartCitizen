package za.co.gundula.app.smartcitizen.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import za.co.gundula.app.smartcitizen.db.SmartCitizenDb;
import za.co.gundula.app.smartcitizen.entity.User;

/**
 * Created by kgundula on 2017/05/31.
 */

public class UserRepositoryImpl implements UserRepository {

    @Inject
    SmartCitizenDb smartCitizenDb;

    public UserRepositoryImpl(SmartCitizenDb smartCitizenDb) {
        this.smartCitizenDb = smartCitizenDb;
    }

    @Override
    public Completable addUser(User user) {
        return Completable.fromAction(() -> smartCitizenDb.userDao().addUser(user));
    }

    @Override
    public LiveData<List<User>> getUser(String userId) {
        return smartCitizenDb.userDao().getUser(userId);
    }

    @Override
    public Completable deleteUser(User user) {
        return Completable.fromAction(() -> smartCitizenDb.userDao().deleteUser(user));
    }
}
