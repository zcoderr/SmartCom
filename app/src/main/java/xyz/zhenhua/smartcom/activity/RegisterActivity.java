package xyz.zhenhua.smartcom.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import xyz.zhenhua.smartcom.R;
import xyz.zhenhua.smartcom.net.FindMessClient;
import xyz.zhenhua.smartcom.net.RegisterClient;
import xyz.zhenhua.smartcom.net.SocThread;
import xyz.zhenhua.smartcom.utils.Utils;
import xyz.zhenhua.smartcom.xmlparse.ParseUser;

import static android.content.ContentValues.TAG;
import static com.baidu.mapapi.BMapManager.getContext;
import static xyz.zhenhua.smartcom.R.id.toolbar;

public class RegisterActivity extends AppCompatActivity {
    EditText username,password;
    Button register;
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            //注册成功
            if(msg.what == Utils.REGISTER_SUCESS){
                Toast.makeText(getContext(),"恭喜!注册成功!",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        username = (EditText)findViewById(R.id.et_reg_usr);
        password = (EditText)findViewById(R.id.et_reg_pwd);
        register = (Button)findViewById(R.id.btn_reg_reg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterClient(handler,username.getText().toString(),password.getText().toString()).start();
                //new FindMessClient().start();
            }
        });


    }


    }

