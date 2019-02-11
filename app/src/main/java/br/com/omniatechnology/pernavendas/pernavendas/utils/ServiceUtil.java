package br.com.omniatechnology.pernavendas.pernavendas.utils;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceUtil {

    public static boolean isRunningService(Context context, String className){

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo item : manager.getRunningServices(Integer.MAX_VALUE)){

            if(item.service.getClassName().equals(className))
                return true;
        }

        return false;
    }

}