package com.qzzx.android.paroute.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import com.qzzx.android.aroute.annotation.enums.RouteType;
import com.qzzx.android.aroute.annotation.mode.PRoteMate;
import com.qzzx.android.paroute.appearance.PostHouse;
import com.qzzx.android.paroute.launcher.PAroute;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2018/8/6 0006.
 * 核心物流
 */

public class CoreLogistics {

    private static CoreLogistics coreLogistics;


    Context mContext;
    ThreadPoolExecutor threadPoolExecutor;


    public static void init(Context context, ThreadPoolExecutor threadPoolExecutor) {
        CoreLogistics coreLogistics = getInstance();
        coreLogistics.threadPoolExecutor = threadPoolExecutor;

        coreLogistics.mContext = context;
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PAroute.wareHouse();
                try {
                    PAroute.loadCargo();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }


    public static CoreLogistics getInstance() {

        if (coreLogistics == null) {

            synchronized (CoreLogistics.class) {

                if (coreLogistics == null) {
                    coreLogistics = new CoreLogistics();
                }
            }

        }
        return coreLogistics;
    }


    public Object sailing(PostHouse postHouse, Context context) {

        final Context currentContext = null == context ? mContext : context;
        if (WareHouse.routes.containsKey(postHouse.getPath())) {


            PRoteMate pRoteMate = WareHouse.routes.get(postHouse.getPath());
            pRoteMate.setType(RouteType.ACTIVITY);
            switch (pRoteMate.getType()) {
                case ACTIVITY:

                    Intent intent = new Intent(currentContext, pRoteMate.getDestination());
                    if (!(context instanceof Activity)) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }

                    ActivityCompat.startActivity(currentContext, intent, postHouse.getExtras());
                    return intent;


                default:

                    try {
                        return pRoteMate.getDestination().newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    break;

            }

        }

        return null;
    }
}
