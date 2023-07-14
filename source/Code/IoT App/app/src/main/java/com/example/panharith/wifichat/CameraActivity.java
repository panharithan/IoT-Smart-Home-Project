package com.example.panharith.wifichat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDatePicker, btnTimePicker , btnTimePickerStop;
    TextView txtDate, txtTime , txtTimeStop;
    ImageView imageView,imageViewcamera;
    Dialog dialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageViewcamera =(ImageView)findViewById(R.id.img_camera);
        imageViewcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CameraActivity.this,LivecameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        dialog = new Dialog(this);

        btnDatePicker= findViewById(R.id.btn_date);
        btnTimePicker= findViewById(R.id.btn_time);
        btnTimePickerStop = findViewById(R.id.btn_stop_time);

        txtDate= findViewById(R.id.in_date);
        txtTime= findViewById(R.id.in_time);
        txtTimeStop = findViewById(R.id.set_time_stop);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnTimePickerStop.setOnClickListener(this);

        ImageView  imageView = findViewById(R.id.ic_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this,PrimaryActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            String AM_PM ;
                            if(hourOfDay < 12) {
                                AM_PM = "AM";

                            } else {
                                AM_PM = "PM";
                                mHour=mHour-12;
                            }

                            txtTime.setText(hourOfDay + ":" + minute+ ""  + AM_PM);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnTimePickerStop) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String AM_PM ;
                            if(hourOfDay < 12) {
                                AM_PM = "AM";

                            } else {
                                AM_PM = "PM";
                                mHour=mHour-12;
                            }

                            txtTimeStop.setText(hourOfDay +  ":"  + minute+ ""  +  AM_PM);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}
