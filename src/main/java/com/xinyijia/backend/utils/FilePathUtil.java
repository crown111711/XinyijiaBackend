package com.xinyijia.backend.utils;

import com.xinyijia.backend.common.BaseConsant;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/16 12:45
 */
public class FilePathUtil {
    public static boolean isWindowsServer() {
        String osName = System.getProperty("os.name");
        String lowerCase = osName.toLowerCase();
        if (lowerCase.startsWith("windows")) {
            return true;
        }
        return false;
    }

    public static String getImagePath(String imageName) {
        String path = null;
        if (isWindowsServer()) {
            path = BaseConsant.WINDOWS_CONST_FILE_PATH + imageName;
        } else {
            path = BaseConsant.LINUX_CONST_FILE_PATH + imageName;
        }
        return path;
    }

    public static String getRootPath() {
        String path = null;
        if (isWindowsServer()) {
            path = BaseConsant.WINDOWS_CONST_FILE_PATH;
        } else {
            path = BaseConsant.LINUX_CONST_FILE_PATH;
        }
        return path;
    }
}
