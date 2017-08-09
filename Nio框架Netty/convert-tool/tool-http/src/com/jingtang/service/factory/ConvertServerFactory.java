/** 
 * Project Name:game-tool <br/>
 * Package Name:com.jingtang.service.factory <br/>
 * File Name:DataConvertFactory.java <br/>
 * Author: Gyang <br/>
 * Date:2017-8-1上午11:35:37 <br/>
 * Copyright (c) 2017, Jingtang Game All Rights Reserved.
 */
package com.jingtang.service.factory;

import java.util.HashMap;
import java.util.Map;

import com.jingtang.constant.ConvertMethod;
import com.jingtang.service.DataConvertServer;
import com.jingtang.service.impl.Amf2JsonService;

/**
 * ClassName: DataConvertFactory <br/>
 * Description: 数据转换业务处理类生产工厂 <br/>
 * Author Gyang <br/>
 * Date: 2017-8-1 上午11:35:37 <br/>
 * 
 * @since JDK 1.7
 */
public class ConvertServerFactory {
    
    private static final Map<Integer, DataConvertServer> SERVERS_MAP = new HashMap<Integer, DataConvertServer>();

    // 注册
    static {
        SERVERS_MAP.put(ConvertMethod.AMF_2_JSON.getType(), new Amf2JsonService());
    }
    /**
     * 
     * Title: buildConvertServer <br/>
     * Description: 根据转换方式构建对应的server处理对象 <br/>
     * 
     * @return: DataConvertServer
     */
    public static DataConvertServer buildServer(int ctype) {
        return SERVERS_MAP.get(ctype);
    }

}
