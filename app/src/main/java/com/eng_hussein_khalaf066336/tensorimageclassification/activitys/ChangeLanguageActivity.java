package com.eng_hussein_khalaf066336.tensorimageclassification.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.tensorimageclassification.ImagClassifier.ImageClassifier;
import com.eng_hussein_khalaf066336.tensorimageclassification.R;

import java.util.Locale;

public class ChangeLanguageActivity extends AppCompatActivity {
    Button Language_btn_AR, Language_btn_EN;
    Locale locale;
    String DefaultLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        Language_btn_AR = findViewById(R.id.Language_btn_ar);
        Language_btn_EN = findViewById(R.id.Language_btn_en);

        locale= ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
        DefaultLanguage=locale.getLanguage();

        Toast.makeText(this, ""+DefaultLanguage, Toast.LENGTH_SHORT).show();

        Language_btn_AR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        setLocale("ar");
            }
        });
        Language_btn_EN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        setLocale("en");
            }
        });
    }


    @SuppressWarnings("deprecation")
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = myLocale;
        getResources().updateConfiguration(conf, dm);

        ImageClassifier.Label_language=lang;

        Intent intent = new Intent(getBaseContext(), SplashScreenActivity.class);
        startActivity(intent);
        finish();

        //  Intent intent = new Intent(getBaseContext(),LanguagActivity.class);
        //  startActivity(intent);
        //finish();
        // startActivity(getIntent());
    }
}