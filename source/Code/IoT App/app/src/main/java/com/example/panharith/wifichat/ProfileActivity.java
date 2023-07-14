package com.example.panharith.wifichat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


//    private SharedPreferenceConfig preferenceConfig;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        SharedPreferenceConfig preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.item = item;
        if (item.getItemId() == R.id.action_home) {
            Intent intent = new Intent(ProfileActivity.this, PrimaryActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(ProfileActivity.this, "home", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(ProfileActivity.this, "Profile", Toast.LENGTH_LONG).show();

        } else if (item.getItemId() == R.id.action_aboutus) {
            Intent intent = new Intent(ProfileActivity.this, AboutusActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(ProfileActivity.this, "About us", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.action_setting) {
            Intent intent = new Intent(ProfileActivity.this, SettingActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(ProfileActivity.this, "setting", Toast.LENGTH_LONG).show();
        }
        return false;
    }




}