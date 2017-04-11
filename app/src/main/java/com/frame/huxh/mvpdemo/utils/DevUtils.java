package com.frame.huxh.mvpdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DevUtils {
    private static final String TAG = "DevUtils";

	private static int screenWidth;
	private static int screenHeight;
	private static float density;
	private static float scaleDensity;
	private static float xdpi;
	private static float ydpi;
	private static int densityDpi;
	
	private static boolean init = false;
	
	private static void init(Context context){
		if (null == context || init) {
			return;
		}
		
		init = true;
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		density = dm.density;
		scaleDensity = dm.scaledDensity;
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		densityDpi = dm.densityDpi;
	}
	
	public static int getScreenWidth(Context context) {
		init(context);
		return screenWidth;
	}
	
	public static int getScreenHeight(Context context) {
		init(context);
		return screenHeight;
	}
	
	public static int getStatusBarHeight(Context context) {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			int sbar = context.getResources().getDimensionPixelSize(x);
			return sbar;
		} catch (Exception e) {
			Log.e("ScreenUtil", "getStatusBarHeight", e);
	        return 0;
		}
	}
	
	public static int getActivityHeight(Activity activity) {
		Rect rect= new Rect();  
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		return rect.height();
	}

	public static int dip2px(Context context, float dipValue) { 
		init(context);
		final float scale = DevUtils.density;
		return (int)(dipValue * scale + 0.5f); 
	} 
	
	public static int px2dip(Context context, float pxValue){
		init(context);
		final float scale = DevUtils.density;
		return (int)((pxValue-0.5)/scale);
	}
	
	public static void hideSoftInput(Activity activity) {
		if(activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
			((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/*** 关闭虚拟键盘 */
    public static void closeInputMethodWindow(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view != null) {
//            view.setFocusable(false);// 若设成true，键盘收了，但是出现焦点问题会导致键盘事件无法传递
//            view.setFocusableInTouchMode(false);
//            view.requestFocus();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

	//显示虚拟键盘
	public static void showInputMethodWindow(View v){
		InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService(Context.INPUT_METHOD_SERVICE );
		imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
	}

    /**
     * 有做M上的权限兼容
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        String deviceID = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                permissionResult = context.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE");
            }
            boolean isPermissionGranted = permissionResult == PackageManager.PERMISSION_GRANTED;
            if (!isPermissionGranted) {
                deviceID = getDeviceIDFromReflection(context);
            } else {
                deviceID = getDeviceIDFromSystem(context);
            }
        } else {
            deviceID = getDeviceIDFromSystem(context);
        }

        return deviceID;
    }

    public static String getDeviceIDFromReflection(Context context) {
        String deviceID = "";
        try {
            Class multiSimUtilsClazz = Class.forName("android.provider.MultiSIMUtils");
            Method getDefaultMethod = multiSimUtilsClazz.getMethod("getDefault", Context.class);
            Object object = getDefaultMethod.invoke(null, context);
            Method method = multiSimUtilsClazz.getMethod("getDeviceId", int.class);
            deviceID = (String) method.invoke(object, 0);
        } catch (Exception e) {
            Log.e(TAG,"getPhoneIMEI: err ", e);
        }

        return deviceID;
    }

    public static String getDeviceIDFromSystem(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = null;
        if(tm != null) {
            try {
                deviceID = tm.getDeviceId();
            } catch (Exception e) {
                Log.e(TAG, "getPhoneIMEIFromTelephony: no phone imei", e);
            }
        }
        return deviceID;
    }

}
