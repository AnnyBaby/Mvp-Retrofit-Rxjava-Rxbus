package com.frame.huxh.mvpdemo.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/31.
 */

public class ToastUtils {
    public static void toast(Context context ,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
}
