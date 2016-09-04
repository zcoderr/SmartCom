package xyz.zhenhua.smartcom.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


import xyz.zhenhua.smartcom.utils.Utils;
import xyz.zhenhua.smartcom.xmlparse.ParseUser;

/**
 * Created by zachary on 16/9/3.
 */

public class RegisterClient extends Thread {
    private String username,password;
    Handler handler;
    String data;
    public RegisterClient(Handler handler,String username,String password){
        ParseUser parseUser = new ParseUser();
        this.handler = handler;
        this.data = parseUser.PackRegister(username,password);
    }
    public void run(){
        try{
            Socket socket = new Socket(Utils.ip,Utils.port);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.i("Register","已建立连接");
            bw.write("");
            bw.flush();
            char c[] = new char[100];
            int l = 0;
            String result;
            while((br.read(c))!=-1){
                result = new String(c,0,l);
                if(result.equals("Hello\0")){
                    bw.write(data);
                    bw.flush();
                    Log.i("Register","已经发送注册信息:"+data);
                }else if(result.equals("")){
                    Message message = new Message();
                    message.what = Utils.REGISTER_SUCESS;
                    handler.sendMessage(message);
                }
            }
        }catch (Exception io){

        }
    }
}
