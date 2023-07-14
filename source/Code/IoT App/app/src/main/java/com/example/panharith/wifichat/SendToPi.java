package com.example.panharith.wifichat;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SendToPi extends AsyncTask<Void,Void,Void>
{

    private static Socket s;
    private static ServerSocket ss;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter printWriter;
     String message = "";
    ServerSocket serverSocketX;
    Thread myThread;
    private static String ip = "192.168.1.113";

    @Override
    protected Void doInBackground(Void... voids)
    {
        try
        {
            s= new Socket(ip,8888);
            printWriter = new PrintWriter(s.getOutputStream());
            printWriter.write(message);
            printWriter.flush();
            printWriter.close();
            //  s.close();
            s.close();
            message="";

        }
        catch (Exception e)
        {
            message="";
        }
        return null;
    }
}
