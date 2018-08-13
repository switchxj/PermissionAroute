package com.qzzx.android.paroute.launcher;

import android.content.Context;

import com.qzzx.android.aroute.annotation.mode.PRoteMate;
import com.qzzx.android.paroute.appearance.PostHouse;
import com.qzzx.android.paroute.appearance.template.IRouterRoot;
import com.qzzx.android.paroute.core.CoreLogistics;
import com.qzzx.android.paroute.core.WareHouse;
import com.qzzx.android.paroute.thread.DefaultPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class PAroute {

    private volatile static ThreadPoolExecutor executor = DefaultPoolExecutor.getInstance();

    public static void init(Context context) {
        CoreLogistics.init(context, executor);
    }

    /**
     * 加载所有加注解的类
     * 加载货物
     */
    public static void loadCargo() throws Exception {

        Map<String, PRoteMate> routes = WareHouse.routes;


        for (String loadClass : WareHouse.IRouterRoots) {

            if (loadClass.contains(".class")) {
                loadClass = loadClass.replace(".class", "");
            }
            IRouterRoot routerRoot = (IRouterRoot) Class.forName(loadClass).newInstance();
            routerRoot.loadInto(routes);

        }

    }


    /**
     * 加载仓库
     */
    public static void wareHouse() {

    }


    public static void loadwareHouse(String rootClass) {
        List<String> wareHouse = WareHouse.IRouterRoots;
        wareHouse.add(rootClass);
    }

    public static PostHouse with(String path) {
        return new PostHouse(path);
    }


    public static Object sailing(Context context, PostHouse postHouse) {

        return CoreLogistics.getInstance().sailing(postHouse, context);
    }
}
