package com.glennbech.prefermentz.preferences;

import android.os.Bundle;
import com.glennbech.prefermentz.R;

/**
 * @author Glenn Bech
 */
public class PreferenceActivity extends android.preference.PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }

}

