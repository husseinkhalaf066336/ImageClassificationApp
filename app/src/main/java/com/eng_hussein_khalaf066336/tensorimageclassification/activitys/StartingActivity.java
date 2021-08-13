package com.eng_hussein_khalaf066336.tensorimageclassification.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eng_hussein_khalaf066336.tensorimageclassification.R;

public class StartingActivity extends AppCompatActivity {
        Button button_IntentMainActivity,button_IntentSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        button_IntentMainActivity = findViewById(R.id.button_IntentMainActivity);
        button_IntentSettings = findViewById(R.id.button_IntentSettings);

        button_IntentMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        button_IntentSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),ChangeLanguageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
