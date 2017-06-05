package za.co.gundula.app.smartcitizen.ui.user;

import android.arch.lifecycle.LifecycleFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import za.co.gundula.app.smartcitizen.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends LifecycleFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
