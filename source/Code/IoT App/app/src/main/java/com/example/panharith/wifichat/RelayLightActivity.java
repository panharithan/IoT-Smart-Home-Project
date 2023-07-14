package com.example.panharith.wifichat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class RelayLightActivity extends AppCompatActivity   implements View.OnClickListener

{
    com.github.angads25.toggle.LabeledSwitch RelaySwitch;
    ImageView imageView;
    Button btnDatePicker, btnTimePicker , btnTimePickerStop;
    TextView txtDate, txtTime , txtTimeStop;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relay_activity);


        btnDatePicker= findViewById(R.id.btn_date);
        btnTimePicker= findViewById(R.id.btn_time);
        btnTimePickerStop = findViewById(R.id.btn_stop_time);

        txtDate= findViewById(R.id.in_date);
        txtTime= findViewById(R.id.in_time);
        txtTimeStop = findViewById(R.id.set_time_stop);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnTimePickerStop.setOnClickListener(this);

        ip = (getString(R.string.ip));


        ImageView imageView = (ImageView) findViewById(R.id.ic_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelayLightActivity.this,PrimaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Thread serverThread = new Thread(new RelayLightActivity.serverThread());
        serverThread.start();
        RelaySwitch = (com.github.angads25.toggle.LabeledSwitch) findViewById(R.id.switch_Relay);
        RelaySwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn)
            {
                if (isOn) //so let's turn if off
                {
                    SendToPi_TurnOffLamp1();
                    Thread serverThread = new Thread(new RelayLightActivity.serverThread());
                    serverThread.start();
                }
                else // it's on. now let's turn it on
                {
                    SendToPi_TurnOnLamp1();
                    Thread serverThread = new Thread(new RelayLightActivity.serverThread());
                    serverThread.start();
                }
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

                            txtTimeStop.setText(hourOfDay + ":" + minute+ ""  + AM_PM);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
    ////////////////
    // non-override functions
    ///////////////////

    private void SendToPi_TurnOnLamp1()
    {
        Toast.makeText(this, "Turn on", Toast.LENGTH_SHORT).show();
        try {

            message = "L.1.1";
            RelayLightActivity.sendToPi_On send = new RelayLightActivity.sendToPi_On();
            send.execute();
            message="";

        }
        catch (Exception e)
        {

        }

    }

    private void SendToPi_TurnOffLamp1()
    {
        Toast.makeText(this, "Turn off", Toast.LENGTH_SHORT).show();

        try {

            RelayLightActivity.sendToPi_Off sendOff = new RelayLightActivity.sendToPi_Off();
            sendOff.execute();
            message="";

        }
        catch (Exception e)
        {

        }    }
    // sendToPi Class
    private static Socket s;
    private static ServerSocket ss;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter printWriter;
    String message , messageSend = "";

    private static String ip = "";

    class sendToPi_On extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                message="1234567";
                s= new Socket(ip,8888);
                //  DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                // dout.writeUTF("Hello Server");
                //dout.flush();

                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.write(message);
                // printWriter.write('0');
                printWriter.flush();
                printWriter.close();
                s.close();
                s.close();
                message="";

            }
            catch (Exception e)
            {
            }
            return null;
        }
    }
    class sendToPi_Off extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                message="1234567";
                s= new Socket(ip,8888);
                //  DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                // dout.writeUTF("Hello Server");
                //dout.flush();

                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.write(message);
                // printWriter.write('0');
                printWriter.flush();
                printWriter.close();
                s.close();
                s.close();
                message="";

            }
            catch (Exception e)
            {
            }
            return null;
        }
    }


    // end of send to PI

    // working as a server
    Button button_sent;
    EditText smessage;
    TextView chat,display_status;
    String str,msg="";
    int serverport = 9999;
    ServerSocket serverSocket;
    Handler handler = new Handler();

    public class serverThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                while(true)
                {
                    serverSocket = new ServerSocket(serverport);
                    Socket client = serverSocket.accept();
                    DataInputStream in = new
                            DataInputStream(client.getInputStream());
                    String line = null;
                    while((line = in.readLine()) != null)
                    {
                        //  msg = msg + "\n Client : " + line;
                        msg = line;
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(RelayLightActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    in.close();
                    client.close();
                    serverSocket.close();
                    Thread.sleep(100);
                    return;
                }
            }
            catch (Exception e)
            {
                return;
            }
        }
    }

}
