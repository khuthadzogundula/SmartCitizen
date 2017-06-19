package za.co.gundula.app.smartcitizen.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.gundula.app.smartcitizen.R;
import za.co.gundula.app.smartcitizen.entity.User;
import za.co.gundula.app.smartcitizen.ui.base.BaseActivity;
import za.co.gundula.app.smartcitizen.ui.user.ProfileActivity;
import za.co.gundula.app.smartcitizen.utils.CircleTransform;
import za.co.gundula.app.smartcitizen.utils.Constanst;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Context context;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefEditor;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.photoHeader)
    View photoHeader;
    @BindView(R.id.mobilenumber)
    TextView phone_number;
    @BindView(R.id.full_name)
    TextView full_name;
    @BindView(R.id.user_avatar)
    ImageView user_avatar;

    @BindView(R.id.properties_recycler)
    RecyclerView properties_recycler;

    @NonNull
    ImageView profileImageView;
    @NonNull
    TextView fullnames,profile_email_address;

    DatabaseReference databaseReference;
    ValueEventListener profileEventListener;

    String profile_fullname, profile_email, uid, profile_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = getApplicationContext();

        setSupportActionBar(toolbar);


        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefEditor = mSharedPref.edit();

        uid = mSharedPref.getString(BaseActivity.USER_UUID, "");
        profile_avatar = mSharedPref.getString(BaseActivity.PROPERTY_AVATAR, "");
        profile_fullname = mSharedPref.getString(BaseActivity.PROPERTY_FULLNAME, "");
        profile_email = mSharedPref.getString(BaseActivity.KEY_SIGNUP_EMAIL, "");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        profileImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        fullnames = (TextView) navigationView.getHeaderView(0).findViewById(R.id.fullnames);
        profile_email_address = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email_address);

        fullnames.setText(profile_fullname);
        profile_email_address.setText(profile_email);

        if (!"".equals(profile_avatar)) {
            displayImageAvatar(profile_avatar);
        }

        // Min SDK is lollipop
        /* For devices equal or higher than lollipop set the translation above everything else */
        photoHeader.setTranslationZ(6);
        /* Redraw the view to show the translation */
        photoHeader.invalidate();


    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(profileEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        profileEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    User user = dataSnapshot.getValue(User.class);
                    full_name.setText(user.getUser_email());
                    phone_number.setText(user.getContact_tel());
                } else {
                    full_name.setText(profile_fullname);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(profileEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void displayImageAvatar(String url) {
        if (url != null) {
            Glide.with(context).load(url)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImageView);

            Glide.with(context).load(url)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(user_avatar);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            mSharedPrefEditor.putString(BaseActivity.USER_UUID, "").apply();
            mSharedPrefEditor.putString(BaseActivity.USER_EMAIL, "").apply();

            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_property) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
