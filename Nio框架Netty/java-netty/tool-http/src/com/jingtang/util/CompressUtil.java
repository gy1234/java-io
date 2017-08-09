package com.jingtang.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * ClassName: CompressConfigUtil Description: 数据压缩和解压 Author Gyang Date: 2017-8-1 上午11:07:15
 * 
 * @since JDK 1.7
 */

public class CompressUtil {
    // 缓存大小
    private static int CACHE_SIZE = 1024;

    /**
     * Title: compressBytes Description: 使用deflater算法,标准zlib压缩数据 *
     * 
     * @return: byte[]
     */
    public static byte[] compressBytes(byte[] input) {
        byte[] output = new byte[0];
        // 使用最大压缩等级(即压缩等级越大,压缩程度越大,压缩后的字节越小)
        Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION, true);
        compresser.reset();
        compresser.setInput(input);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        try {
            byte[] buf = new byte[CACHE_SIZE];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = input;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

    /**
     * Title: decompressBytes <br/>
     * Description: 使用inflater算法,标准zlib解压缩数据, 与deflater压缩对应使用 <br/>
     * @return: byte[]
     */
    public static byte[] decompressBytes(byte[] input) {
        byte[] output = new byte[0];
        Inflater decompresser = new Inflater(true);
        decompresser.reset();
        decompresser.setInput(input);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        try {
            byte[] buf = new byte[CACHE_SIZE];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = input;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        decompresser.end();
        return output;
    }

    // 测试
    public static void main(String[] args) {
        Map<Object, Object>  map = new HashMap<>();
        map.put("k1", "abc");
        map.put(100, 100.2D);
        map.put("arr", new int[] { 1, 2, 3 });
        
        String inputStr = "snowolf@zlex.org;dongliang@zlex.org;zlex.dongliang@zl";
        System.err.println("输入字符串:\t" + inputStr);
        byte[] input = inputStr.getBytes();
        System.err.println("输入字节长度:\t" + input.length);

        byte[] data = compressBytes(input);
        System.err.println("压缩后字节长度:\t" + data.length);

        byte[] output = decompressBytes(data);
        System.err.println("解压缩后字节长度:\t" + output.length);
        String outputStr = new String(output);
        System.err.println("输出字符串:\t" + outputStr);
    }
}
