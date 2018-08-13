package com.qzzx.android.paroute.appearance.template;

import com.qzzx.android.aroute.annotation.mode.PRoteMate;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public interface IRouterRoot {


    /**
     * Load routes to input
     *
     * @param routes input
     */
    void loadInto(Map<String, PRoteMate> routes);

}
