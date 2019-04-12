package com.herbwen.hostactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Environment;
import android.util.ArrayMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Herrrb", "resIds:" + R.layout.activity_main);
        Log.i("Herrrb", "context1:" + this.getApplicationContext());
        Log.i("Herrrb", "context2:" + this.getPackageResourcePath());

        TextView button = findViewById(R.id.button);
        button.setText("test inject");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });
    }

    @SuppressLint("NewApi")
    public void click(){
        String filesDir = Environment.getExternalStorageDirectory().getPath();
        String libPath = filesDir + File.separator + "Plugin.apk";
//        String libPath = filesDir + File.separator + "PluginActivity1.apk";
//        Log.i("Herrrb", "file exits:" + new File(libPath).exists());

        DexClassLoader loader = new DexClassLoader(libPath, filesDir, filesDir, getClassLoader());

        Class<?> clazz = null;

        try {
            clazz = loader.loadClass("com.herbwen.pluginactivity.MainActivity");
//            clazz = loader.loadClass("com.example.dynamicactivityapk.MainActivity");
            Log.i("Herrrb", "loaded MainActivity of Plugin.");

//            Class rClazz = loader.loadClass("com.herbwen.pluginactivity.R$layout");
////            Class rClazz = loader.loadClass("com.example.dynamicactivityapk.R$layout");
//
//            Field field = rClazz.getField("activity_main");
//            Integer obj = (Integer)field.get(null);

//            View view = LayoutInflater.from(MainActivity.this).inflate(obj, null);
//            Log.i("Herrrb", "LayoutInflater");

            Method method1 = clazz.getMethod("PluginLog", String.class);

            method1.invoke(null, "就权当我的插件调用成功了");
//            Log.i("Herrrb", "field" + obj);
        }catch (Throwable e){
            Log.i("Herrrb", "error: "+ Log.getStackTraceString(e));
            e.printStackTrace();
        }

        loadApkClassLoader(loader);

        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
    }

    @SuppressLint("NewApi")
    private void loadApkClassLoader(DexClassLoader dLoader){
        try {
            String filesDir = this.getCacheDir().getAbsolutePath();
//            String libPath = filesDir + File.separator + "Plugin.apk";
            String libPath = filesDir + File.separator +"PluginActivity1.apk";

            Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread", new Class[]{}, new Object[]{});

            String packageName = this.getPackageName();

            ArrayMap mPackages = (ArrayMap) RefInvoke.getFieldObject("android.app.ActivityThread", currentActivityThread, "mPackages");
            WeakReference wr = (WeakReference) mPackages.get(packageName);
            RefInvoke.setFieldObject("android.app.LoadedApk", "mClassLoader", wr.get(), dLoader);

            Log.i("Herrrb", "classloader:" + dLoader);
        }catch (Exception e){
            Log.i("Herrrb", "load apk classloader error: " + Log.getStackTraceString(e));
        }
    }

//    private Field getField(Class<?> cls, String name){
//        for (Field field: cls.getDeclaredFields()){
//            if (!field.isAccessible()){
//                field.setAccessible(true);
//            }
//            if (field.getName().equals(name)){
//                return field;
//            }
//        }
//        return null;
//    }

//    private void inject(DexClassLoader loader){
//        PathClassLoader pathClassLoader = (PathClassLoader) getClassLoader();
//
//        try {
//            Object dexElements = combineArray()
//        }
//    }
//
//    private static Object combineArray(Object arrayLhs, Object arrayRhs)
}

