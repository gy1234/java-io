package com.jingtang.config;

import java.util.ResourceBundle;

public class GameConfig {

    private static ResourceBundle bundle = ResourceBundle.getBundle("game-config");

    // ================================AMF转JSON=================================
    /**
     * 获取amf2json文件接收地址路径
     */
    public static String amf2JsonInPath() {
        return bundle.getString("amf2json.in.path");
    }

    /**
     * 获取amf2json文件接收过滤后缀名
     */
    public static String amf2JsonInSuffix() {
        return bundle.getString("amf2json.in.suffix");
    }

    /**
     * 获取amf2json文件输出地址路径
     */
    public static String amf2JsonOutPath() {
        return bundle.getString("amf2json.out.path");
    }

    /**
     * 获取amf2json文件输出过滤后缀名
     */
    public static String amf2JsonOutSuffix() {
        return bundle.getString("amf2json.out.suffix");
    }

}
