package com.hzjytech.hades.aidllearn.utils;


import java.io.Closeable;
import java.io.IOException;

/**
 * Created by zhanghehe on 2017/12/12.
 */

public class MyUtils {
    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
