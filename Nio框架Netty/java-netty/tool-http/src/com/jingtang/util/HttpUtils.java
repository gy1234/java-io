package com.jingtang.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * ClassName: HttpUtils <br/>
 * Description: http网络下载文件数据 <br/>
 * Author Gyang <br/>
 * Date: 2017-8-1 上午11:22:23 <br/>
 * 
 * @since JDK 1.7
 */
public class HttpUtils {

    /**
     * Title: download <br/>
     * Description: 输出网络文件的字节数组 <br/>
     * 
     * @return: byte[]
     */
    public static byte[] download(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int tmp;
        while ((tmp = in.read()) != -1) {
            out.write(tmp);
        }
        byte[] data = out.toByteArray();
        in.close();
        out.flush();
        out.close();
        return data;

    }

}
