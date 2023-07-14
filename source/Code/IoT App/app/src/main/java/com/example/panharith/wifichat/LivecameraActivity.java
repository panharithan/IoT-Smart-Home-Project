package com.example.panharith.wifichat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class LivecameraActivity extends AppCompatActivity {

    TextView textView;
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_camera);

        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
       // webView.loadUrl("https://192.168.1.110:5000/video_feed");
        webView.loadUrl("https://www.youtube.com");

        TextView textView =(TextView) findViewById(R.id.txtclose);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LivecameraActivity.this,CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
