package za.co.gundula.app.smartcitizen.ui.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import za.co.gundula.app.smartcitizen.R;
import za.co.gundula.app.smartcitizen.ui.login.LoginActivity;

/**
 * Created by kgundula on 2017/05/24.
 */

public abstract class BaseActivity extends AppCompatActivity implements
        OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static String LOG_TAG = BaseActivity.class.getName();

    public static final String KEY_SIGNUP_EMAIL = "SIGNUP_EMAIL";
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_GOOGLE_EMAIL = "GOOGLE_EMAIL";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String PROPERTY_AVATAR = "property_avatar";
    public static final String PROPERTY_UID = "property_uid";
    public static final String USER_UUID = "user_uid";
    public static final String PROPERTY_USERNAME = "property_username";
    public static final String PROPERTY_FULLNAME = "property_fullname";

    protected String mProvider, mEncodedEmail;

    protected GoogleApiClient mGoogleApiClient;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup the Google API object to allow Google logins */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        /**
         * Build a GoogleApiClient with access to the Google Sign-In API and the
         * options specified by gso.
         */

        /* Setup the Google API object to allow Google+ logins */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
        mEncodedEmail = sp.getString(KEY_GOOGLE_EMAIL, null);
        mProvider = sp.getString(KEY_PROVIDER, null);


        if (!(this instanceof LoginActivity) ) {


            mAuth = FirebaseAuth.getInstance();
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {

                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString(KEY_GOOGLE_EMAIL, "");
                        spe.putString(KEY_PROVIDER, "");

                        takeUserToLoginScreenOnUnAuth();
                    }

                }
            };

            mAuth.addAuthStateListener(mAuthListener);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void logout() {

        /* Logout if mProvider is not null */
        if (mProvider != null) {
            FirebaseAuth.getInstance().signOut();

            if (mProvider.equals(GOOGLE_PROVIDER)) {

                /* Logout from Google+ */
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                //nothing
                            }
                        });
            }
        }
    }

    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
