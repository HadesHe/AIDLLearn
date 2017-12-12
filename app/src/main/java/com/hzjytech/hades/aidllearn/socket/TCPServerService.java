package com.hzjytech.hades.aidllearn.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hzjytech.hades.aidllearn.utils.MyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {

    private boolean mIsServiceDestroyed=false;

    private String[] mDefinedMessages=new String[]{
      "Hello",
            "What's your name",
            "What a nice day today is",
            "Do you know, I could chat with many people",
            ""
    };
    public TCPServerService() {
    }


    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed=true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable{

        @Override
        public void run() {
            ServerSocket serverSocket=null;

            try {
                serverSocket=new ServerSocket(8688);
            } catch (IOException e) {
                System.err.println("establish tcp server failed , port:8688");
                e.printStackTrace();
                return;
            }

            while(!mIsServiceDestroyed){
                try {
                    final Socket client=serverSocket.accept();
                    System.out.println("accept");

                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void responseClient(Socket client) throws IOException {
        BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
        out.println("Welcome to chat room");
        while (!mIsServiceDestroyed){
            String str=in.readLine();
            System.out.println("msg from client:"+str);
            if(str==null){
                break;
            }

            int i=new Random().nextInt(mDefinedMessages.length);
            String msg=mDefinedMessages[i];
            out.println(msg);
            System.out.println("send :"+msg);
        }

        System.out.println("client quit.");
        MyUtils.close(out);
        MyUtils.close(in);
        client.close();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
