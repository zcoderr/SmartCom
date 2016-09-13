package xyz.zhenhua.smartcom.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import xyz.zhenhua.smartcom.entity.Mess;
import xyz.zhenhua.smartcom.utils.Utils;
import xyz.zhenhua.smartcom.xmlparse.MessParser;

/**
 * Created by zachary on 16/9/11.
 */

public class FindMessClient extends Thread{
    private Handler handler;
    public FindMessClient(Handler handler){
        this.handler = handler;
    }
    public void run(){
        try{
            Socket socket = new Socket(Utils.ip,Utils.port);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Log.i("FidnClient","成功获取连接");
            bw.write("USER_FINDMESS\0");
            bw.flush();
            while(true){
                char []c = new char[1000];
                int l = br.read(c);
                String s = new String(c,0,l);
                if(s.equals("Hello\0")){
                    Log.i("FindMess",s);
                    bw.write(" ");
                    bw.flush();
                }else{
                    //String s_utf8 = new String(s.getBytes("GB2312"),"UTF-8");
                    Mess mess = MessParser.parseMess(s);
                   // String  ss = new String(s.getBytes("gb2312"),"utf-8");
                    Log.i("FindMess",s);
                    Message message = new Message();
                    message.what = Utils.PEO_LIST;
                    message.obj = mess;
                    handler.sendMessage(message);
                    bw.close();
                    br.close();
                    socket.close();
                }

            }




        }catch (Exception e){

        }
    }
}
