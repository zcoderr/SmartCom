package xyz.zhenhua.smartcom.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import xyz.zhenhua.smartcom.R;
import xyz.zhenhua.smartcom.net.LoginClient;
import xyz.zhenhua.smartcom.net.LogoutClient;
import xyz.zhenhua.smartcom.net.SocThread;
import xyz.zhenhua.smartcom.utils.Utils;
import xyz.zhenhua.smartcom.xmlparse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private Button login, register,logout;
    EditText username, password;
    TextView stat;
    SharedPreferences mySharedPreferences;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //登录成功
            if(msg.what == Utils.LOGIN_SUCESS){
                String d = (String) msg.obj;
                Log.i("handler","收到:"+d);
                setSharedPreferences(d);
                }else if(msg.what == Utils.LOGIN_FAILE){
                    Toast.makeText(getContext(),"登录失败!请检查用户名和密码。",Toast.LENGTH_SHORT).show();
                }else if(msg.what == Utils.LOGOUT_SUCCESS){
                    delSharedPreferences();
                }
            }


    };




    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login = (Button) getView().findViewById(R.id.btn_login_lg);
        register = (Button) getView().findViewById(R.id.btn_login_rg);
        username = (EditText) getView().findViewById(R.id.et_login_usr);
        password = (EditText) getView().findViewById(R.id.et_login_pwd);
        logout = (Button)getView().findViewById(R.id.btn_login_logout);
        stat = (TextView)getView().findViewById(R.id.tv_login_stat);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginClient(handler,"USER_LOGIN\0",username.getText().toString(),password.getText().toString()).start();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySharedPreferences = getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
                String key = mySharedPreferences.getString("key","");
                String username = mySharedPreferences.getString("username","");
                new LogoutClient(username,key,handler).start();
            }
        });

        getState();


    }


    public void setSharedPreferences(String key){
        mySharedPreferences = getContext().getSharedPreferences("account",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("username",username.getText().toString());
        editor.putString("key",key);
        editor.commit();
        Toast.makeText(getContext(),"登录成功,key:"+key,Toast.LENGTH_SHORT).show();
        logoutView();
    }
    public void delSharedPreferences(){
        mySharedPreferences = getContext().getSharedPreferences("account",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.clear().commit();
        Toast.makeText(getContext(),"登出成功",Toast.LENGTH_SHORT).show();
        loginView();
    }
    public void getState() {
        mySharedPreferences = getContext().getSharedPreferences("account",
                Context.MODE_PRIVATE);
        String key = mySharedPreferences.getString("key","");
        String username = mySharedPreferences.getString("username","");
        if(key.length()>0){
            logoutView();
            stat.setText("用户 "+username+",已登录。");
        }else{
            loginView();
        }

    }
    public void loginView(){
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
        stat.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
    }
    public void logoutView(){
        username.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        stat.setVisibility(View.VISIBLE);
        logout.setVisibility(View.VISIBLE);
    }



    }








