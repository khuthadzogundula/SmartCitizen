package za.co.gundula.app.smartcitizen.ui.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import za.co.gundula.app.smartcitizen.injection.SmartCitizenComponent;
import za.co.gundula.app.smartcitizen.repository.UserRepository;

/**
 * Created by kgundula on 2017/05/31.
 */

public class ProfileViewModel extends ViewModel implements SmartCitizenComponent.Injectable {


    @Inject
    UserRepository userRepository;
    /*
    private MutableLiveData<String> user_email = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> contact_tel = new MutableLiveData<>();
    private MutableLiveData<String> user_initials = new MutableLiveData<>();
    private MutableLiveData<String> user_first_name = new MutableLiveData<>();
    private MutableLiveData<String> user_surname = new MutableLiveData<>();
    private MutableLiveData<String> date_created = new MutableLiveData<>();
    private MutableLiveData<String> date_updated = new MutableLiveData<>();
    */
    public ProfileViewModel() {
        //return userRepository;
    }

    @Override
    public void inject(SmartCitizenComponent smartCitizenComponent) {
        smartCitizenComponent.inject(this);
    }
}
