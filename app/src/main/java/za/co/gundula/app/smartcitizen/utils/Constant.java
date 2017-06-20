package za.co.gundula.app.smartcitizen.utils;

import za.co.gundula.app.smartcitizen.BuildConfig;

/**
 * Created by kgundula on 2017/06/14.
 */

public class Constant {

    public static final String FIREBASE_LOCATION_USERS = "users";

    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    public static final String PREF_USER_FIRST_TIME = "first_time_install";
}
