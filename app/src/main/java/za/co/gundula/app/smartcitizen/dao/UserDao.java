package za.co.gundula.app.smartcitizen.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import za.co.gundula.app.smartcitizen.entity.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by kgundula on 2017/05/22.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.USER_ID + " > :userId")
    LiveData<List<User>> getUser(String userId);

    @Insert(onConflict = REPLACE)
    void addUser(User user);

    @Delete
    void deleteUser(User user);

    @Update(onConflict = REPLACE)
    void updateUser(User user);

}
