package za.co.gundula.app.smartcitizen.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static za.co.gundula.app.smartcitizen.entity.User.TABLE_NAME;

/**
 * Created by kgundula on 2017/05/22.
 */

@Entity(tableName = TABLE_NAME)
public class User {

    public static final String TABLE_NAME = "user";
    public static final String USER_CREATED_DATE = "date_created";
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "user_email";


    @PrimaryKey @ColumnInfo(name = USER_ID)
    private String user_id;
    @ColumnInfo(name = USER_EMAIL)
    private String user_email;
    private String username;
    private String contact_tel;
    private String user_initials;
    private String user_first_name;
    private String user_surname;
    @ColumnInfo(name = USER_CREATED_DATE)
    private String date_created;

    public User(String user_id, String user_email, String username, String contact_tel, String user_initials, String user_first_name, String user_surname, String date_created) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.username = username;
        this.contact_tel = contact_tel;
        this.user_initials = user_initials;
        this.user_first_name = user_first_name;
        this.user_surname = user_surname;
        this.date_created = date_created;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact_tel() {
        return contact_tel;
    }

    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }

    public String getUser_initials() {
        return user_initials;
    }

    public void setUser_initials(String user_initials) {
        this.user_initials = user_initials;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user_id", user_id);
        result.put("user_email", user_email);
        result.put("username", username);
        result.put("contact_tel", contact_tel);
        result.put("user_initials", user_initials);
        result.put("user_first_name", user_first_name);
        result.put("user_surname", user_surname);
        result.put("date_created", date_created);

        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_email='" + user_email + '\'' +
                ", username='" + username + '\'' +
                ", contact_tel='" + contact_tel + '\'' +
                ", user_initials='" + user_initials + '\'' +
                ", user_first_name='" + user_first_name + '\'' +
                ", user_surname='" + user_surname + '\'' +
                ", date_created='" + date_created + '\'' +
                '}';
    }

}
