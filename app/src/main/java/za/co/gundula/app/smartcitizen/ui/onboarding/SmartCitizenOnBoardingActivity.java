package za.co.gundula.app.smartcitizen.ui.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

import za.co.gundula.app.smartcitizen.R;
import za.co.gundula.app.smartcitizen.ui.login.LoginActivity;
import za.co.gundula.app.smartcitizen.ui.main.MainActivity;
import za.co.gundula.app.smartcitizen.utils.Constant;

public class SmartCitizenOnBoardingActivity extends AhoyOnboarderActivity {


    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean firstUse = mSharedPref.getBoolean(Constant.PREF_USER_FIRST_TIME, true);

        if (firstUse) {
            mSharedPrefEditor = mSharedPref.edit();
            mSharedPrefEditor.putBoolean(Constant.PREF_USER_FIRST_TIME, false).apply();
            initializeAhoyOnBoarding();
        } else {
            onBoardingDone();
        }
    }

    @Override
    public void onFinishButtonPressed() {
        onBoardingDone();
    }

    public void onBoardingDone() {
        Intent intentMain = new Intent(SmartCitizenOnBoardingActivity.this, LoginActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentMain);
        finish();
    }

    public void initializeAhoyOnBoarding() {

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Capture Reading", "You will be able to capture meter readings on your property for submission to the municipality (City of Tshwane)", R.drawable.capture_reading);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("View Reading", "View Meter reading history and usage graph", R.drawable.view_reading);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Notificaton", "Receive notification reminders to send capture and send meter readings.", R.drawable.notification);

        ahoyOnboarderCard1.setBackgroundColor(R.color.white);
        ahoyOnboarderCard2.setBackgroundColor(R.color.white);
        ahoyOnboarderCard3.setBackgroundColor(R.color.white);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.grey_600);
        }

        setFinishButtonTitle("Finish");
        showNavigationControls(false);

        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.solid_one);
        colorList.add(R.color.solid_two);
        colorList.add(R.color.solid_three);

        setColorBackground(colorList);

        //Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        //setFont(face);

        setOnboardPages(pages);

    }
}
