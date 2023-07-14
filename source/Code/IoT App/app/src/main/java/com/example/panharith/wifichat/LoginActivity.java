package com.example.panharith.wifichat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity{
    private SharedPreferenceConfig preferenceConfig;
    private EditText txt_username,txt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);

        if (preferenceConfig.readLoginStatus()){
            startActivity(new Intent(this,PrimaryActivity.class));
            finish();
        }
    }

    public void loginUser(View view) {
        String userName = txt_username.getText().toString();
        String userPassword = txt_password.getText().toString();


        if (userName.equals(getResources().getString(R.string.user_name)) &&
                userPassword.equals(getResources().getString(R.string.user_password))){

            startActivity(new Intent(this,PrimaryActivity.class));
            preferenceConfig.writeLoginStatus(true);

            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show();
            txt_username.setText("");
            txt_password.setText("");
        }
    }
}
