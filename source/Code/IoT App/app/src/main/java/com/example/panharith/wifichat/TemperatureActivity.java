package com.example.panharith.wifichat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TemperatureActivity extends AppCompatActivity
    implements View.OnClickListener
{
    TextView tvTempe ;
    Button btnReload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_acitvity);
        btnReload = (Button) findViewById(R.id.button_temp_reload);
        btnReload.setOnClickListener(this);
        tvTempe = (TextView) findViewById(R.id.textview_temp);
        //Thread thread = new Thread(new ListenToServer_Temperature());
        //thread.start();
        //RequestTemp();

        ip = (getString(R.string.ip));
//        Toast.makeText(this,ip,Toast.LENGTH_LONG).show();

        ImageView imageView = findViewById(R.id.ic_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TemperatureActivity.this,PrimaryActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id==R.id.button_temp_reload)
        {
            RequestTemp();
        }
    }
    /////
    private void RequestTemp()
    {
        try
        {
            SendToPi_RequestTemperature send = new SendToPi_RequestTemperature();
            send.execute();
            message="";
         //  Thread thread = new Thread(new ListenToServer_Temperature());
           // thread.start();
        }
        catch (Exception e)
        {

        }
    }
    //
    private static Socket s;
    private static ServerSocket ss;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter printWriter;
    String message , messageSend = "";

    private static String ip = "";


    class SendToPi_RequestTemperature extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                message="123456789"; // code for
                s= new Socket(ip,8888);
                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.write(message);
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


    ///////////////////////
    ///////////////////////
    //////
    ///
    // working as a server
    Button button_sent;
    EditText smessage;
    TextView chat,display_status;
    String str,msg="";
    int serverport = 9999;
    ServerSocket serverSocket;
    Handler handler = new Handler();

    public class ListenToServer_Temperature implements Runnable
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
                                tvTempe.setText(msg + " °C");
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
