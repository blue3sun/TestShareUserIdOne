package com.android.monkey.testshareuseridone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TEST";
    private ImageView mIvImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mIvImage = (ImageView)findViewById(R.id.iv_image);
        findViewById(R.id.btn_switch).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchToSecondPage();
                    }
                }
        );
        findViewById(R.id.btn_tag).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getValue();
                    }
                }
        );
        findViewById(R.id.btn_sharedpreference).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSpValue();
                    }
                }
        );
        findViewById(R.id.btn_image).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayImage();
                    }
                }
        );
    }

    private void displayImage() {
        if(mIvImage.getVisibility()==View.VISIBLE){
            mIvImage.setVisibility(View.GONE);
        }else{
            try {
                Context context = createPackageContext("com.android.monkey.testshareuseridtwo", Context.CONTEXT_INCLUDE_CODE);
                int resId = context.getResources().getIdentifier("koala","mipmap","com.android.monkey.testshareuseridtwo");
                Log.e(TAG, "displayImage: resId是"+resId);
                if(resId!=0){
                    mIvImage.setImageResource(resId);
                    mIvImage.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.e(TAG, "displayImage: 出异常" );
                e.printStackTrace();
            }
        }
    }

    private void getSpValue() {
        try {
            Context context = createPackageContext("com.android.monkey.testshareuseridtwo",Context.CONTEXT_INCLUDE_CODE);
            SharedPreferences sp = context.getSharedPreferences("pref", MODE_PRIVATE);
            String keyValue = sp.getString("key","null");
            Toast.makeText(this, "keyValue的值是："+keyValue, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "getSpValue: 出异常" );
            e.printStackTrace();
        }
    }

    private void getValue() {
        try {
            Context context = createPackageContext("com.android.monkey.testshareuseridtwo",Context.CONTEXT_INCLUDE_CODE);
            //Class<?> clazz = Class.forName("com.android.monkey.testshareuseridtwo.MainActivity");
            Class<?> clazz = context.getClassLoader().loadClass("com.android.monkey.testshareuseridtwo.MainActivity");
            Field tag = clazz.getDeclaredField("mTag");
            tag.setAccessible(true);
            Log.e(TAG, "getValue: tag"+tag);
        } catch (Exception e) {
            Log.e(TAG, "getValue: 出异常" );
            e.printStackTrace();
        }
    }

    /**跳转至另一个页面*/
    private void switchToSecondPage() {
        try {
            Context context = createPackageContext("com.android.monkey.testshareuseridtwo",Context.CONTEXT_INCLUDE_CODE);
            //Intent intent = new Intent(context,Class.forName("com.android.monkey.testshareuseridtwo.MainActivity"));
            //Intent intent = new Intent(context,context.getClassLoader().loadClass("com.android.monkey.testshareuseridtwo.MainActivity"));

            Intent intent = new Intent();
            intent.setClassName(context,"com.android.monkey.testshareuseridtwo.MainActivity");
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "switchToSecondPage: 出异常" );
            e.printStackTrace();
        }
    }
}
