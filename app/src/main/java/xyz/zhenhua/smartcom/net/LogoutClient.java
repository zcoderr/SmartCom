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
 * Created by zachary on 16/9/1.
 */

public class LogoutClient extends Thread {
    String key,username,data;
    String ip = Utils.ip;
    int port = Utils.port;
    ParseUser parseUser;
    Handler handler;
    public LogoutClient(String username, String key, Handler handler){
        parseUser = new ParseUser();
        data = parseUser.PackLogout(username,key);
        this.handler = handler;
    }
    public void run(){
        try{
            Log.i("Logout","建立连接中");
            Socket socket = new Socket(ip,port);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.i("logout","获取输入输出流成功");
            bw.write("USER_LOGOUT\0");
            bw.flush();
            char c[] = new char[100];
            int l = 0;
            String result;
            while ((l=br.read(c))!=-1){
                result = new String(c,0,l);
                Log.i("LogOut",result);
                if(result.equals("Hello\0")){
                    bw.write(data);
                    bw.flush();
                    Log.i("LogOut","已发送注销请求:"+data);
                }if(result.equals("Logout_succ")){
                    Message message = new Message();
                    message.what = Utils.LOGOUT_SUCCESS;
                    handler.sendMessage(message);
                }
            }



        }catch (Exception e){}


    }
}
