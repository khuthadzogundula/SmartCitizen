package za.co.gundula.app.smartcitizen.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import za.co.gundula.app.smartcitizen.SmartCitizenApplication;

/**
 * Created by kgundula on 2017/05/31.
 */

public class SmartCitizenFactory extends ViewModelProvider.NewInstanceFactory {

    private SmartCitizenApplication smartCitizenApplication;

    public SmartCitizenFactory(SmartCitizenApplication smartCitizenApplication) {
        this.smartCitizenApplication = smartCitizenApplication;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof SmartCitizenComponent.Injectable) {
            ((SmartCitizenComponent.Injectable) t).inject(smartCitizenApplication.getSmartCitizenComponent());
        }
        return t;
    }
}
