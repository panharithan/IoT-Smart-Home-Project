package com.example.panharith.wifichat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity{
    Intent intent;
    ImageView imageView;
    private SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        ImageView imageView = findViewById(R.id.ic_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,PrimaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void userLogout(View view) {
        preferenceConfig.writeLoginStatus(false);
        startActivity( new Intent(this,LoginActivity.class));
        finish();
        Toast.makeText(SettingActivity.this, "Logout Account", Toast.LENGTH_LONG).show();
    }


}
