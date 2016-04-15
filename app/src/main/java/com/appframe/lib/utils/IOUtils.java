package com.appframe.lib.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Roy
 * Date: 15/11/23
 */
public class IOUtils {
    public static void closeCloseable(Closeable obj) {
        try {
            // 修复小米MI2的JarFile没有实现Closeable导致崩溃问题
            if (obj instanceof Closeable && obj != null)
                obj.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
