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

public class FenceActivity extends AppCompatActivity
    implements View.OnClickListener
{
    Button btnUp,btnDown,btnOpen,btnClose;
    TextView tvStatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fence_activity);
        btnUp = (Button) findViewById(R.id.fence_up);
        btnDown = (Button) findViewById(R.id.fence_down);
        btnOpen = (Button) findViewById(R.id.fence_Open);
        btnClose = (Button) findViewById(R.id.fence_close);
        btnUp.setOnClickListener(this);
        btnDown.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        tvStatus = (TextView) findViewById(R.id.textview_fence_status);

        ip = (getString(R.string.ip));

        ImageView imageView = findViewById(R.id.ic_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FenceActivity.this,PrimaryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
     int id = v.getId();
     if (id==R.id.fence_up)
     {
         try
         {
             SendToPi_Fence_Up send = new SendToPi_Fence_Up();
             send.execute();
             message="";
             //Thread thread = new Thread(new ListenToServer_Fence());
             //thread.start();

         }
         catch (Exception e)
         {

         }
        // Toast.makeText(this,"UP",Toast.LENGTH_LONG).show();
     }
     else if (id==R.id.fence_down)
     {
         try
         {
             SendToPi_Fence_Down send = new SendToPi_Fence_Down();
             send.execute();
             message="";
             Thread thread = new Thread(new ListenToServer_Fence());
             thread.start();

         }
         catch (Exception e)
         {

         }
        // Toast.makeText(this,"Down",Toast.LENGTH_LONG).show();

     }
     else if (id==R.id.fence_Open)
     {
         try
         {
             SendToPi_Fence_Open send = new SendToPi_Fence_Open();
             send.execute();
             message="";
             Thread thread = new Thread(new ListenToServer_Fence());
             thread.start();

         }
         catch (Exception e)
         {

         }
        // Toast.makeText(this,"Open",Toast.LENGTH_LONG).show();
     }
     else if (id==R.id.fence_close)
     {
         try
         {
             SendToPi_Fence_Close send = new SendToPi_Fence_Close();
             send.execute();
             message="";
             Thread thread = new Thread(new ListenToServer_Fence());
             thread.start();

         }
         catch (Exception e)
         {

         }
        // Toast.makeText(this,"Close",Toast.LENGTH_LONG).show();
     }
     else
     {

     }
    }// end of onclick

    ///////////////////////////////////
    // class sendToPi_Fence_up
    // sendToPi Class
    private static Socket s;
    private static ServerSocket ss;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter printWriter;
    String message , messageSend = "";

    private static String ip = "";

    class SendToPi_Fence_Up extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                message="1234567890A"; // code for fence up 15 degree
                s= new Socket(ip,8888);
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
    class SendToPi_Fence_Down extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                message="1234567890AB"; // code for fence up 15 degree
                s= new Socket(ip,8888);
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
    class SendToPi_Fence_Open extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                message="1234567890ABC"; // code for fence up 15 degree
                s= new Socket(ip,8888);
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
    class SendToPi_Fence_Close extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                message="1234567890ABCD"; // code for fence up 15 degree
                s= new Socket(ip,8888);
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

    public class ListenToServer_Fence implements Runnable
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
                                tvStatus.setText(msg);
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
