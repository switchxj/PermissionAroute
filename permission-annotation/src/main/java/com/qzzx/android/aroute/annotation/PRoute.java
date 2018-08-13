package com.qzzx.android.aroute.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/8/4 0004.
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface PRoute {


    /**
     * Path of route
     */
    String path();

    /**
     * Used to merger routes, the group name MUST BE USE THE COMMON WORDS !!!
     */
    String group() default "";

    /**
     * Name of route, used to generate jc.avado
     */
    String name() default "undefined";

    /**
     * Extra data, can be set by user.
     * Ps. U should use the integer num sign the switch, by bits. 10001010101010
     */
    int extras() default Integer.MIN_VALUE;

    /**
     * The priority of route.
     */
    int priority() default -1;

    /**
     * 对应String文件 的资源Id
     *
     * @return
     */
    int stringNameId() default -1;


    /**
     * 对应 图片ID
     * @return
     */
    int imageId() default -1;


}
