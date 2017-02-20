package com.example.dysaniazzz.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by DysaniazzZ on 2016/9/10.
 * 流操作的工具类
 */
public class StreamUtils {

    /**
     * 关流的方法
     *
     * @param stream
     */
    public static void endStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
