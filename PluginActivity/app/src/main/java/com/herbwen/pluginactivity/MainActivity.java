package com.herbwen.pluginactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    private static View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (parentView == null){
//            setContentView(R.layout.activity_main);
//        }else {
//            setContentView(parentView);
//        }
//
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "调用插件成功", Toast.LENGTH_SHORT).show();
//            }
//        });

        Log.i("Herrrb", "插件调用成功");
    }

//    public static void setLayoutView(View view){
//        parentView = view;
//    }

    public static void PluginLog(String s){
        Log.i("Herrrb", s);
    }
}
