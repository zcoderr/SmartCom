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
import xyz.zhenhua.smartcom.xmlparse.ParseSendData;
import xyz.zhenhua.smartcom.xmlparse.ParseUser;

/**
 * Created by zachary on 16/9/1.
 */

public class SeekHelpClient extends Thread {
    String username,title,buf,key,data;
    Double x,y;
    Handler handler;
    String ip = Utils.ip;
    int port = Utils.port;
    ParseSendData parseSendData;
    public SeekHelpClient(String usrname, String title,String buf, Double x, Double y,String key, Handler handler){
        this.username = usrname;
        this.title = title;
        this.x = x;
        this.y = y;
        this.handler = handler;
        parseSendData = new ParseSendData();
        data = parseSendData.packData(username,title,buf,key,x,y);
    }

    public void run(){
        try{
            Socket socket = new Socket(ip,port);
            Log.i("SeekHelp","正在建立连接");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Log.i("SeekHelp","获取输入输出流成功");
            bw.write("USER_SENDMESS\0");
            bw.flush();
            String result = new String();
            char c[] = new char[100];
            int l = 0;
            while((l=br.read(c))!=-1){
                result = new String(c,0,l);
                Log.i("SeekHelp","收到数据"+result);
                if(result.equals("Hello\0")){
                    bw.write(data);
                    bw.flush();
                    Log.i("SeekHelp","已经发送求助信息:"+data);
                }else if(result.equals("Send_succ")){
                    Message message = new Message();
                    message.what = Utils.SENDHELP_SUCCESS;
                    handler.sendMessage(message);
                }



            }

        }catch (Exception e){

        }finally {

        }

    }
}
