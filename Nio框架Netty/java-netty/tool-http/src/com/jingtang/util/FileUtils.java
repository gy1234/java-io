package com.jingtang.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * ClassName: FileUtils <br/>
 * Description: 文件操作工具 <br/>
 * Author Gyang <br/>
 * Date: 2017-8-1 上午11:19:26 <br/>
 * 
 * @since JDK 1.7
 */

public class FileUtils {

    /**
     * Title: mkdir <br/>
     * Description: 递归创建目录 <br/>
     * 
     * @return: void
     */
    public static void mkdir(File file) {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                mkdir(file.getParentFile());
            }
            file.mkdir();
        }
    }

    /**
     * Title: mkFile <br/>
     * Description: 递归创建文件全路径 <br/>
     * 
     * @return: void
     */
    public static void mkFile(File file) {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                mkdir(file.getParentFile());
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Title: delFile <br/>
     * Description: 递归删除目录全部 <br/>
     * 
     * @return: void
     */
    public static void delFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {

                File[] files = file.listFiles();
                if (null != files) {
                    for (File tmp : files) {
                        delFile(tmp);
                    }
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }

    /**
     * Title: write2File <br/>
     * Description: 将data数据写入到file中 <br/>
     * 
     * @return: void
     */
    public static void write2File(File file, byte[] data) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(data);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
