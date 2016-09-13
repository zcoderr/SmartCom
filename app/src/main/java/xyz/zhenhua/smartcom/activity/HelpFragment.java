package xyz.zhenhua.smartcom.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import xyz.zhenhua.smartcom.R;
import xyz.zhenhua.smartcom.net.SeekHelpClient;
import xyz.zhenhua.smartcom.utils.Utils;
//求助者发布求助信息
/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {
    EditText title,buf;
    Button send;
    SharedPreferences mySharedPreferences,sharedPreferences2;
    double x;
    double y;

    Handler handler = new Handler() {
      public void handleMessage(Message msg){
            if(msg.what==Utils.SENDHELP_SUCCESS){
                Toast.makeText(getContext(),"您的求助信息已发送,请耐心等待。",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),WaitActivity.class));
            }
      }
    };
    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title = (EditText)getView().findViewById(R.id.et_seekhelp_title);
        buf = (EditText)getView().findViewById(R.id.et_seekhelp_buf);
        send = (Button)getView().findViewById(R.id.btn_seekhelp_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySharedPreferences = getContext().getSharedPreferences("account",
                        Context.MODE_PRIVATE);
                String key = mySharedPreferences.getString("key",null);
                String username = mySharedPreferences.getString("username","");
                if(key==null){
                    Toast.makeText(getContext(),"您还没有登录,请登录后操作!",Toast.LENGTH_SHORT).show();
                }else{
                    sharedPreferences2 = getContext().getSharedPreferences("location",Context.MODE_PRIVATE);
                    String sx = sharedPreferences2.getString("x","1.1");
                    String sy = sharedPreferences2.getString("y","1.1");
                    x = Double.parseDouble(sx);
                    y = Double.parseDouble(sy);
                    new SeekHelpClient(username,title.getText().toString(),buf.getText().toString(),x,y,key,handler).start();
                }
            }
        });
    }
    public void getState() {}

}
