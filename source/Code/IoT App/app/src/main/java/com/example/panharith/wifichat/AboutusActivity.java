package com.example.panharith.wifichat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class AboutusActivity extends AppCompatActivity {

    Intent intent;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        ImageView imageView = (ImageView) findViewById(R.id.ic_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutusActivity.this,PrimaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

