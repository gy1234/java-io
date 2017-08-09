package com.jingtang.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.exadel.flamingo.flex.messaging.amf.io.AMF3Deserializer;

/**
 * ClassName: ConfigUtils <br/>
 * Description: 配置数据解析工具 <br/>
 * Author Gyang <br/>
 * Date: 2017-8-1 上午11:20:50 <br/>
 * 
 * @since JDK 1.7
 */
public class ConfigUtils {

    /**
     * Title: getResource <br/>
     * Description: 输出解析后的java对象 <br/>
     * 
     * @return: Object
     */
    public static Object getResource(byte[] fileData) throws IOException {
        ByteArrayInputStream in = null;
        AMF3Deserializer deserializer = null;
        try {
            byte[] data = CompressUtil.decompressBytes(fileData);
            in = new ByteArrayInputStream(data);
            deserializer = new AMF3Deserializer(in);
            return deserializer.readObject();
        } finally {
            try {
                in.close();
                deserializer.close();
            } catch (Exception e2) {
                System.err.println(e2);
            }
        }
    }

}
