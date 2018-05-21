package com.xinyijia.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/21 12:38
 */
@Slf4j
public class HostUtil {

    public static String getIp() {
        String host = "localhost";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String local = inetAddress.getHostAddress();
            if (StringUtils.isNotBlank(local)) {
                host = local;
                log.info("--------host:{}",host);
            }
        } catch (UnknownHostException e) {
        }

        return host;
    }
}
