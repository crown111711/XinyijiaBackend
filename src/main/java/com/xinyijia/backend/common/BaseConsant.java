package com.xinyijia.backend.common;

import com.xinyijia.backend.utils.FilePathUtil;
import com.xinyijia.backend.utils.HostUtil;

import javax.mail.internet.InternetAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/12 19:44
 */
public class BaseConsant {
   static  String host ="localhost";

    static{
        //host = HostUtil.getIp();
     if(!FilePathUtil.isWindowsServer()){
         host = "120.78.128.119";
     }
    }

    public static final String BASE_PATH = "/xyj/api";

    public static final String  WINDOWS_CONST_FILE_PATH = "E:\\var\\www\\image\\";
    public static final String LINUX_CONST_FILE_PATH = "/var/www/image/";

    public static final String USER_ICON_IMAGE = "user";

    public static final String PRODUCT_ICON_IMAGE ="product_icon";

    public static final String ADD_PRODUCT_SUCCESS = "添加商品成功";

    public static final String DOWN_FILE = "download";

    public static final String SHOW_IMAGE = "http://"+host+":8090/xyj/api/attachment/showImage/";

}
