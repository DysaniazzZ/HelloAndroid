package com.example.dysaniazzz.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Dysania on 2016/9/10.
 * 关于流的操作（单例模式）
 */
public class StreamUtils {

    //私有构造
    private StreamUtils(){}

    //私有静态成员变量
    private static StreamUtils sStreamUtils = new StreamUtils();

    //公共静态的获取实例方法
    public static StreamUtils getInstance() {
        return sStreamUtils;
    }

    /**
     * 关流的方法
     * @param stream
     */
    public static void endStream(Closeable stream) {
        if(stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
