package za.co.gundula.app.smartcitizen.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.sql.Time;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;
import za.co.gundula.app.smartcitizen.R;
import za.co.gundula.app.smartcitizen.ui.main.MainActivity;
import za.co.gundula.app.smartcitizen.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public static final int RC_SIGN_IN = 1;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefEditor;


    private ProgressDialog mAuthProgressDialog;

    SignInButton google_sign_in;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefEditor = mSharedPref.edit();

        initializeScreen();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                String user_uid = user.getUid();
                Log.i(LOG_TAG, user_uid);
                mSharedPrefEditor.putString(BaseActivity.USER_UUID, user_uid).apply();
                showOnBoarding();
            }
        };

    }

    public void initializeScreen() {
        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mAuthProgressDialog.setCancelable(false);
    }

    @OnClick(R.id.google_sign_in)
    void onGoogleSignInClick(View v) {
        signIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Cleans up listeners tied to the user's authentication state
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mAuthProgressDialog.dismiss();
        showSnackBar(connectionResult.toString());
    }


    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        mAuthProgressDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                if (result.getStatus().getStatusCode() == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                    showSnackBar("The sign in was cancelled. Make sure you're connected to the internet and try again.");
                } else {
                    showSnackBar("Error handling the sign in: " + result.getStatus().getStatusMessage());
                }
                mAuthProgressDialog.dismiss();

            }
        }
    }


    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public void showOnBoarding() {

        //On Loading you only need to go to the Main Activity - 16-08-2016 12:53 - Nokwanda
        //Intent intentMain = new Intent(LoginActivity.this, ConfirmationScreen.class);
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentMain);
        finish();

    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(LOG_TAG, acct.toString());
                            String unprocessedEmail = acct.getEmail();
                            String user_uid = acct.getId();
                            String user_name = acct.getDisplayName();
                            String fullname = acct.getGivenName()+" "+acct.getFamilyName();
                            Log.i(LOG_TAG, acct.getIdToken());

                            mSharedPrefEditor.putString(BaseActivity.KEY_SIGNUP_EMAIL, unprocessedEmail).apply();
                            mSharedPrefEditor.putString(BaseActivity.KEY_PROVIDER, BaseActivity.GOOGLE_PROVIDER).apply();
                            mSharedPrefEditor.putString(BaseActivity.PROPERTY_USERNAME, user_name).apply();
                            mSharedPrefEditor.putString(BaseActivity.PROPERTY_FULLNAME, fullname).apply();

                            if (acct.getPhotoUrl() != null) {
                                final String imageUrl = acct.getPhotoUrl().toString();
                                mSharedPrefEditor.putString(PROPERTY_AVATAR, imageUrl).apply();
                            } else {
                                mSharedPrefEditor.putString(BaseActivity.PROPERTY_AVATAR, "").apply();
                            }

                            mSharedPrefEditor.commit();

                            showOnBoarding();
                        } else {
                            // If sign in fails, display a message to the user.
                            Timber.d(LOG_TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        mAuthProgressDialog.dismiss();
                    }

                });
    }

}
