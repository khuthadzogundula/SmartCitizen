package za.co.gundula.app.smartcitizen.ui.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import za.co.gundula.app.smartcitizen.entity.User;
import za.co.gundula.app.smartcitizen.injection.SmartCitizenComponent;
import za.co.gundula.app.smartcitizen.repository.UserRepository;
import za.co.gundula.app.smartcitizen.ui.base.BaseActivity;

/**
 * Created by kgundula on 2017/05/31.
 */

public class ProfileViewModel extends ViewModel implements SmartCitizenComponent.Injectable {


    @Inject
    UserRepository userRepository;
    private String displayName;
    private String emailAddress;
    private String phoneNumber;
    private String user_id;
    private String initials;
    private String first_name;
    private String surname;
    private String date_created;


    public ProfileViewModel() {
        //return userRepository;
        //user_id = mSharedPref.getString(BaseActivity.PROPERTY_UID, "");
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void addUser(String user_id) {

        //initials =
        User user = new User(user_id, emailAddress, displayName, phoneNumber, initials, first_name, surname, date_created);
        /*userRepository.addUser(user).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - successfully added user");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError - add:", e);
                    }
                });
         */
    }

    @Override
    public void inject(SmartCitizenComponent smartCitizenComponent) {
        smartCitizenComponent.inject(this);
    }
}
