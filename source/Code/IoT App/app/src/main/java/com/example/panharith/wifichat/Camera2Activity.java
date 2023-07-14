package com.example.panharith.wifichat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Camera2Activity extends AppCompatActivity
{
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {


            setContentView(R.layout.activity_camera2);
            webView = findViewById(R.id.webview2);
            webView.setWebViewClient(new WebViewClient());
           // webView.loadUrl("https://www.google.com");
            webView.loadUrl("http://192.168.0.105:5000");

        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onBackPressed()
    {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }


    }
}
