package com.glennbech.prefermentz;

import android.os.Bundle;

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

