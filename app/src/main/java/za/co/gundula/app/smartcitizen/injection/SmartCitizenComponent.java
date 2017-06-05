package za.co.gundula.app.smartcitizen.injection;

import javax.inject.Singleton;

import dagger.Component;
import za.co.gundula.app.smartcitizen.ui.user.ProfileViewModel;

/**
 * Created by kgundula on 2017/05/31.
 */

@Singleton
@Component(modules = {SmartCitizenModule.class})
public interface SmartCitizenComponent {

    void inject(ProfileViewModel profileViewModel);

    interface Injectable {
        void inject(SmartCitizenComponent smartCitizenComponent);
    }
}
