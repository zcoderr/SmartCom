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
    String ip = Utils.ip;
    int port = 6000;
   // String data;
    public RegisterClient(Handler handler,String username,String password){
        this.username = username;
        this.password = password;
        this.handler = handler;
        //this.data = parseUser.PackRegister(username,password);
    }
    public void run(){
        try{
            Log.i("RegClient","正在创建连接");
            Socket socket = new Socket(ip,port);
            Log.i("RegClient","获取连接中");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Log.i("RegClient","获取输入输出流成功");
            bw.write("USER_CREATE\0");
           // Log.i("LoginClient","已发送:"+data);
            bw.flush();
            String result = new String();
            char []c = new char[100];
            int l = 0;
            while((l=br.read(c))!=-1){
                result = new String(c,0,l);
                Log.i("RegClient","收到:"+result);

                if(result.equals("Hello\0")){
                    ParseUser parseUser = new ParseUser();
                    String user = parseUser.PackUser(username,password);
                    bw.write(user);
                    bw.flush();
                    //sleep(1000);
                    Log.i("RegClient","已发送:"+user);
                    Message message = new Message();
                    message.what = Utils.REGISTER_SUCESS;
                    message.obj = result;
                    handler.sendMessage(message);
                }



            }
            bw.close();
            br.close();
            socket.close();
        }catch (Exception e){

        }finally {

        }
    }
}
