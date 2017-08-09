package com.jingtang.service.impl;

import java.io.File;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.jingtang.config.GameConfig;
import com.jingtang.service.DataConvertServer;
import com.jingtang.util.ConfigUtils;
import com.jingtang.util.FileUtils;
import com.jingtang.util.HttpUtils;

public class Amf2JsonService implements DataConvertServer{

    @Override
    public String doConvert(String fileName) {
        // 1.下载文件到本地
        String inFile = fileName + GameConfig.amf2JsonInSuffix();
        byte[] fileData = null;
        try {
            fileData = HttpUtils.download(new URL(GameConfig.amf2JsonInPath() + inFile));
        } catch (Exception e) {
            return "文件下载失败!";
        }
        if (null == fileData) {
            return "文件不存在!";
        }
        // 2.amf解析数据
        Object data = null;
        try {
            data = ConfigUtils.getResource(fileData);
        } catch (Exception e) {
            return "文件解析失败!";
        }
        if (null == data) {
            return "文件解析失败!";
        }
        // 3.json序列化文件
        byte[] json = JSON.toJSONString(data).getBytes();
        String outFile = GameConfig.amf2JsonOutPath() + fileName + GameConfig.amf2JsonOutSuffix();
        try {
            File file = new File(outFile);
            FileUtils.mkFile(file);
            FileUtils.write2File(file, json);
        } catch (Exception e) {
            return "文件转换失败!";
        }
        return "success";
    }
}
