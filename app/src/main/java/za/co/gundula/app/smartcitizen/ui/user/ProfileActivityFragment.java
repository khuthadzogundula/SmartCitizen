package za.co.gundula.app.smartcitizen.ui.user;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.gundula.app.smartcitizen.R;
import za.co.gundula.app.smartcitizen.SmartCitizenApplication;
import za.co.gundula.app.smartcitizen.entity.User;
import za.co.gundula.app.smartcitizen.injection.SmartCitizenFactory;
import za.co.gundula.app.smartcitizen.ui.base.BaseActivity;
import za.co.gundula.app.smartcitizen.utils.CircleTransform;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends LifecycleFragment {

    @BindView(R.id.display_name)
    EditText editTextDisplayName;

    @BindView(R.id.email_address)
    EditText editTextEmailAddress;

    @BindView(R.id.phone_number)
    EditText editTextPhoneNumber;

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.process_bar)
    ProgressBar process_bar;

    private ProfileViewModel profileViewModel;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefEditor;

    private DatabaseReference mDatabase;

    String uid, profile_avatar, email_address;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        //mSharedPrefEditor = mSharedPref.edit();

        uid = mSharedPref.getString(BaseActivity.USER_UUID, "");
        email_address = mSharedPref.getString(BaseActivity.USER_EMAIL, "");
        profile_avatar = mSharedPref.getString(BaseActivity.PROPERTY_AVATAR, "");
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        setupViewModel();
        setupEventListeners();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProfilePicture(profile_avatar);
        editTextEmailAddress.setText(email_address);
        mDatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User user = dataSnapshot.getValue(User.class);
                    editTextDisplayName.setText(user.getUsername());
                    editTextPhoneNumber.setText(user.getContact_tel());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadProfilePicture(String pic_url) {
        if (pic_url != null) {
            process_bar.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(pic_url).transform(new CircleTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    process_bar.setVisibility(View.GONE);
                    return false;
                }
            }).into(avatar);
        }

    }

    private void setupEventListeners() {

        editTextDisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                profileViewModel.setDisplayName(s.toString());
            }
        });

        editTextEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                profileViewModel.setEmailAddress(s.toString());
            }
        });

        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                profileViewModel.setPhoneNumber(s.toString());
            }
        });


    }

    private void setupViewModel() {

        SmartCitizenApplication smartCitizenApplication = (SmartCitizenApplication) getActivity().getApplication();
        profileViewModel = ViewModelProviders.of(this, new SmartCitizenFactory(smartCitizenApplication)).get(ProfileViewModel.class);
        editTextDisplayName.setText(profileViewModel.getDisplayName());
        editTextEmailAddress.setText(profileViewModel.getEmailAddress());
        editTextPhoneNumber.setText(profileViewModel.getPhoneNumber());
    }

    @OnClick(R.id.profile_update)
    void updateProfile() {
        
        boolean update_user = true;
        if (TextUtils.isEmpty(editTextDisplayName.getText().toString().trim())) { // Check for a valid email address.
            editTextDisplayName.setError(getString(R.string.full_name));
            update_user = false;
        } else if (TextUtils.isEmpty(editTextEmailAddress.getText().toString().trim())) { // Check for a valid email address.
            editTextEmailAddress.setError(getString(R.string.email_address_required));
            update_user = false;
        } else if (!isEmailValid(editTextEmailAddress.getText().toString().trim())) {
            editTextEmailAddress.setError(String.format(getString(R.string.error_invalid_email), editTextEmailAddress.getText().toString().trim()));
            update_user = false;
        }

        if (update_user) {
            profileViewModel.addUser(uid);
        }

    }

    public boolean isEmailValid(String email) {
        return (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @OnClick(R.id.change_profile_pic)
    void updateProfilePic() {


    }
}
