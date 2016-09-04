package xyz.zhenhua.smartcom.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.Buffer;

import xyz.zhenhua.smartcom.utils.Utils;
import xyz.zhenhua.smartcom.xmlparse.ParseUser;


/**
 * Created by zachary on 16/8/31.
 */

public class LoginClient extends Thread {
    Handler handler;
    String data,username,password;
    String ip = Utils.ip;
    int port = 6000;
    public LoginClient(Handler handler, String data, String username, String password){
        this.handler = handler;
        this.data = data;
        this.username = username;
        this.password = password;
    }

    public void run(){
        try{
            Log.i("LoginClient","正在创建连接");
            Socket socket = new Socket(ip,port);
            Log.i("LoginClient","获取连接中");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Log.i("LoginClient","获取输入输出流成功");
            bw.write(data+"\n");
            Log.i("LoginClient","已发送:"+data);
            bw.flush();
            String result = new String();
            char []c = new char[100];
            int l = 0;
            while((l=br.read(c))!=-1){
                result = new String(c,0,l);
                Log.i("LoginClient","收到:"+result);

                if(result.equals("Hello\0")){
                    ParseUser parseUser = new ParseUser();
                    String user = parseUser.PackUser(username,password);
                    bw.write(user);
                    bw.flush();
                    Log.i("LoginClient","已发送:"+user);
                }else if(result.equals("Login_failed")){
                    Message message = new Message();
                    message.what = Utils.LOGIN_FAILE;
                    handler.sendMessage(message);
                } else{
                    Message message = new Message();
                    message.what = Utils.LOGIN_SUCESS;
                    message.obj = result;
                    handler.sendMessage(message);
                }



            }
//            if((result=br.readLine())!=null){
//                Log.i("服务器返回",result);
//                Message message = new Message();
//                message.what = 0;
//                message.obj = result;
//                handler.sendMessage(message);
//            }
//            bw.close();
//            br.close();
//            socket.close();
        }catch (Exception e){

        }finally {

        }
    }
}
