package com.qzzx.android.aroute.annotation.mode;

import com.qzzx.android.aroute.annotation.PRoute;
import com.qzzx.android.aroute.annotation.enums.RouteType;

import java.util.Map;

import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class PRoteMate {


    private RouteType type;         // Type of route
    private Element rawType;        // Raw type of route
    private Class<?> destination;   // Destination
    private String path;            // Path of route
    private String group;           // Group of route
    private int priority = -1;      // The smaller the number, the higher the priority
    private int extra;              // Extra data

    private String title; //标题
    private int titleId = -1; //标题对应的String ID

    private int drawableId = -1; //图片对应的drwableID




    private Map<String, Integer> paramsType;  // Param type

    public PRoteMate() {
    }


    /**
     * For versions of 'compiler' less than 1.0.7, contain 1.0.7
     *
     * @param type        type
     * @param destination destination
     * @param path        path
     * @param group       group
     * @param priority    priority
     * @param extra       extra
     * @return this
     */
    public static PRoteMate build(RouteType type, Class<?> destination, String path, String group, int priority, int extra) {
        return new PRoteMate(type, null, destination, path, group, null, priority, extra);
    }


    /**
     * For versions of 'compiler' greater than 1.0.7
     *
     * @param type        type
     * @param destination destination
     * @param path        path
     * @param group       group
     * @param paramsType  paramsType
     * @param priority    priority
     * @param extra       extra
     * @return this
     */
    public static PRoteMate build(RouteType type, Class<?> destination, String path, String group, Map<String, Integer> paramsType, int priority, int extra) {
        return new PRoteMate(type, null, destination, path, group, paramsType, priority, extra);
    }


    public static PRoteMate build(String path, Class<?> destination, int titleId, int drawableId) {

        PRoteMate pRoteMate = new PRoteMate();
        pRoteMate.setPath(path);
        pRoteMate.setDestination(destination);
        pRoteMate.setTitleId(titleId);
        pRoteMate.setDrawableId(drawableId);
        return pRoteMate;
    }

    /**
     * Type
     *
     * @param route       route
     * @param destination destination
     * @param type        type
     */
    public PRoteMate(PRoute route, Class<?> destination, RouteType type) {

        this(type, null, destination, route.path(), route.group(), null, route.priority(), route.extras());

        this.titleId = route.stringNameId();

        this.drawableId = route.imageId();

    }

    /**
     * Type
     *
     * @param route      route
     * @param rawType    rawType
     * @param type       type
     * @param paramsType paramsType
     */
    public PRoteMate(PRoute route, Element rawType, RouteType type, Map<String, Integer> paramsType) {
        this(type, rawType, null, route.path(), route.group(), paramsType, route.priority(), route.extras());
    }

    /**
     * Type
     *
     * @param type        type
     * @param rawType     rawType
     * @param destination destination
     * @param path        path
     * @param group       group
     * @param paramsType  paramsType
     * @param priority    priority
     * @param extra       extra
     */
    public PRoteMate(RouteType type, Element rawType, Class<?> destination, String path, String group, Map<String, Integer> paramsType, int priority, int extra) {
        this.type = type;
        this.destination = destination;
        this.rawType = rawType;
        this.path = path;
        this.group = group;
        this.paramsType = paramsType;
        this.priority = priority;
        this.extra = extra;
    }

    public Map<String, Integer> getParamsType() {
        return paramsType;
    }

    public PRoteMate setParamsType(Map<String, Integer> paramsType) {
        this.paramsType = paramsType;
        return this;
    }

    public Element getRawType() {
        return rawType;
    }

    public PRoteMate setRawType(Element rawType) {
        this.rawType = rawType;
        return this;
    }

    public RouteType getType() {
        return type;
    }

    public PRoteMate setType(RouteType type) {
        this.type = type;
        return this;
    }

    public Class<?> getDestination() {
        return destination;
    }

    public PRoteMate setDestination(Class<?> destination) {
        this.destination = destination;
        return this;
    }

    public String getPath() {
        return path;
    }

    public PRoteMate setPath(String path) {
        this.path = path;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public PRoteMate setGroup(String group) {
        this.group = group;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public PRoteMate setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public int getExtra() {
        return extra;
    }

    public PRoteMate setExtra(int extra) {
        this.extra = extra;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    @Override
    public String toString() {
        return "PRoteMate{" +
                "type=" + type +
                ", rawType=" + rawType +
                ", destination=" + destination +
                ", path='" + path + '\'' +
                ", group='" + group + '\'' +
                ", priority=" + priority +
                ", extra=" + extra +
                '}';
    }


}
