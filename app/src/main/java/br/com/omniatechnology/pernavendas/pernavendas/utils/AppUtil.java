package br.com.omniatechnology.pernavendas.pernavendas.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtil {

    public static Long versionCode(Context context){

        PackageInfo packageInfo = getPackageInfo(context);

        if(packageInfo==null){
            return 0l;
        }

        return packageInfo.getLongVersionCode();
    }

    public static String versionName(Context context){

        PackageInfo packageInfo = getPackageInfo(context);

        if(packageInfo==null){
            return "";
        }

        return packageInfo.versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {

        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return pInfo;
    }
}
