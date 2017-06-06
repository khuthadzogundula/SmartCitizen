package za.co.gundula.app.smartcitizen.ui.user;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.gundula.app.smartcitizen.R;
import za.co.gundula.app.smartcitizen.SmartCitizenApplication;
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


    String uid, profile_avatar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        //mSharedPrefEditor = mSharedPref.edit();

        uid = mSharedPref.getString(BaseActivity.USER_UUID, "");
        profile_avatar = mSharedPref.getString(BaseActivity.PROPERTY_AVATAR, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        setupViewModel();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProfilePicture(profile_avatar);
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

    private void setupViewModel() {

        SmartCitizenApplication smartCitizenApplication = (SmartCitizenApplication) getActivity().getApplication();
        profileViewModel = ViewModelProviders.of(this, new SmartCitizenFactory(smartCitizenApplication)).get(ProfileViewModel.class);
        editTextDisplayName.setText(profileViewModel.getDisplayName());
        editTextEmailAddress.setText(profileViewModel.getEmailAddress());
        editTextPhoneNumber.setText(profileViewModel.getPhoneNumber());
    }

    @OnClick(R.id.profile_update)
    void updateProfile() {
        Toast.makeText(getContext(), uid, Toast.LENGTH_LONG).show();
        profileViewModel.addUser(uid);
    }

    @OnClick(R.id.change_profile_pic)
    void updateProfilePic() {


    }
}
