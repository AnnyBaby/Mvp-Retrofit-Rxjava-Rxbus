package com.frame.huxh.mvpdemo.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AppInfoUtils {
    
    public static final String TAG = AppInfoUtils.class.getSimpleName();
    public static final int ANDROIDM_VERSION = 23;
    /** 
     * 是否是系统软件或者是系统软件的更新软件 
     * @return 
     */  
    public static boolean isSystemApp(PackageInfo pInfo) {  
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);  
    }  
  
    public static boolean isSystemUpdateApp(PackageInfo pInfo) {  
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);  
    }  
  
    public static boolean isUserApp(PackageInfo pInfo) {  
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));  
    }
    
    /**
     * 是出厂内置应用，即系统应用  且 系统没更新的应用
     * @param pInfo
     * @return
     */
    public static boolean isBuiltInApp(PackageInfo pInfo) {  
        return (isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));  
    }
    

    /**
     * 获取当前顶层应用调用的包名
     * @param context
     * @return
     */
    public static String getTopActivityPackageName(Context context) {  
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am!=null) {
        	ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        	return cn.getPackageName(); 
		}
        return null;
    }
    
    /**
     * 获取当前栈顶的Activity
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {  
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName(); 
    }
    
    /**
     * 获取应用启动图标
     * @param context
     * @return
     * @throws Exception
     */
    public static Drawable getApplicationIconDrawable(Context context) throws Exception {  
        PackageManager pm = context.getPackageManager();
        String packageName = getTopActivityPackageName(context);
        PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
        Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
        return drawable;

    }
    
    /**
     * 获取应用名
     * @param context
     * @return
     * @throws Exception
     */
	public static String getApplicationName(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return null;
		}
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			String name = (String) packageInfo.applicationInfo.loadLabel(pm);
			return name;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
     * 获取应用信息
     * @param context
     * @return
     */
	public static Object[] getApplicationInfo(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return null;
		}
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			Object[] infos = new Object[3];
			infos[0] = packageInfo.applicationInfo.loadLabel(pm).toString();
			infos[1] = packageInfo.versionCode;
			infos[2] = packageInfo.versionName;
			return infos;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static boolean isAppExist(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		try {
			PackageManager pm = context.getPackageManager();
			return pm.getPackageInfo(packageName, 0) != null;
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * 获取指定包名的应用版本号
     * @param context
     * @return
     */
    public static int getVersionCodeByPkg(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return -1;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return packageInfo.versionCode;
        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * 获取指定包名的应用版本
     * @param context
     * @return
     */
    public static String getVersionNameByPkg(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return packageInfo.versionName;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取手机IMEI
     * 需要权限：<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneIMEI = null;
        if(tm != null) {
            try {
                phoneIMEI = tm.getDeviceId();
            } catch (Exception e) {
                Log.e(TAG, "no imei");
            }
        }
        return phoneIMEI;
    }
    
    /**
     * 获取手机型号
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取Android版本号
     * @param context
     * @return
     */
    public static String getAndroidVersion(Context context) {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统版本号
     * @return
     */
    public static String getRomVersion() {
        return android.os.Build.DISPLAY;
    }

    /**
     * 获取手机mac地址<br/>
     * 注意：手机刷机后 必须开一次 wifi 功能 系统才能获取到mac【未验证过】
     */
//    public static String getMacAddress(Context context) {
//        String macAddress = "";
//        try {
//            WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
//            if (null != info) {
//                if(null!=info.getMacAddress()){
//                    return info.getMacAddress();
//                }
//            }
//        } catch (Exception e) {
//            return macAddress;
//        }
//        return macAddress;
//    }


    public static String getSerialNumber1() {
        return android.os.Build.SERIAL;
    }

    /**
     * getSerialNumber
     * @return result is same to getSerialNumber1()
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return serial;
    }

    public static String getSimSerialNumber(Context context) {
        return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();

    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本号]
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取系统语言
     * @param context
     * @return
     */
    public static String getLanguage(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getCountry();
    }

    /**
     * 获取分辨率
     * @return
     */
    public static String getResolution(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return new StringBuilder(String.valueOf(height)).append("x")
                .append(String.valueOf(width)).toString();
    }

    /**
     * 获取屏幕密度dpi
     * @return
     */
    public static String getDensityDpi(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return String.valueOf(displayMetrics.densityDpi);
    }

    /**
     * 获取设备唯一ID
     * @param context
     * @return
     */
//    public static String getDevid(Context context) {
//    	DevCacheModel devCacheModel = ModelFactory.getDevCacheModel(context);
//        String devid = devCacheModel.getDevid();
//        if(!TextUtils.isEmpty(devid)) {
//            return devid;
//        } else {
//            String imei = getIMEI(context);
//            StringBuilder sb = new StringBuilder();
//            if(!TextUtils.isEmpty(imei)) {
//                sb.append(imei);
//            }
//
//            String androidId = getAndroidId(context);
//            if(!TextUtils.isEmpty(androidId)) { //androidId在恢复出厂设置后会被重置
//                sb.append(androidId);
//            }
//
//            if(sb.length() == 0) { //应该是不可能发生的事情，即使imei为空，androidId也不会为空
//                sb.append(UUID.randomUUID().toString());
//            }
//            devid = MD5Util.encode(sb.toString());
//            devCacheModel.saveDevid(devid);
//            return devid;
//        }
//    }


    /**
     * 有做M上的权限兼容
     * @param context
     * @return
     */
    public static String getPhoneIMEI(Context context) {
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

        Log.i(TAG,"getPhoneIMEI : " + deviceID);
        return deviceID;
    }

    /**
     * 这个反射方式获取DeviceId，在有SIM卡情况下，结果与走系统通道获取的一致，
     * 但在无SIM卡情况下，与系统通道的不一致，需要再看下什么情况 ?_?
     * @param context
     * @return
     */
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

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * 获取操作系统版本
     * @return
     */
    public static String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取操作系统SDK版本
     * @return
     */
    public static String getOsSdkVersion() {
        return android.os.Build.VERSION.SDK;
    }

    /**
     * 获取手机制造商
     * @return
     */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }
    /**
     * 获取品牌
     * @return
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }
    
    /**

     * 获取手机服务商信息 <BR>
     * 需要加入权限<uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/> <BR>
     */

    public static String getProvidersName(Context context) {
        String providersName = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        if(IMSI != null){
         // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providersName = "中国电信";
            }
        } else {
            providersName = null;
        }
        return providersName;
    }

    
    /**
     * 得到当前的手机网络类型
     * 
     * @param context
     * @return
     */ 
    public static String getCurrentNetType(Context context) { 
        String type = ""; 
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo info = cm.getActiveNetworkInfo(); 
        if (info == null) { 
            type = "null"; 
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) { 
            type = "wifi"; 
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) { 
            int subType = info.getSubtype(); 
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS 
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) { 
                type = "2g"; 
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA 
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0 
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) { 
                type = "3g"; 
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准 
                type = "4g"; 
            } 
        } 
        return type; 
    }
    
    /**
     * 查找某个配置了ACTION_MAIN的指定类是否存在
     * @param cls
     * @return
     */
    public static boolean isExistByMainAction(Context mContext, String cls){
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);  
        List<ResolveInfo>  resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
        if(resolveInfo.size() == 0){
            return false;
        }else{
            for(ResolveInfo info : resolveInfo){
                if(!TextUtils.isEmpty(cls) && cls.equals(info.activityInfo.name)){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 检测该包名所对应的应用是否存在
     * @param packageName
     * @return
     */
    public static boolean checkPackage(Context mContext, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            mContext.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            Log.e(TAG, "checkPackage()", e);
            return false;
        }
    }
    
    public static RunningAppProcessInfo getCurProcessInfo(Context context) {
		List<RunningAppProcessInfo> list = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (list == null) return null;
		int pid = 1;
		for(RunningAppProcessInfo info : list) {
			if(pid == info.pid) {
				return info;
			}
		}
		return null;
	}
    
    public static boolean isForeground(Context context) {
    	RunningAppProcessInfo curProcessInfo = getCurProcessInfo(context);
    	if (curProcessInfo!=null) {
    		return curProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
		}
    	return false;
    }

    public static boolean isNeedInsertPermission(){
        if (Build.VERSION.SDK_INT >= ANDROIDM_VERSION){
            return true;
        }else {
            return false;
        }
    }
}
