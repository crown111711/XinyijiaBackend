package com.xinyijia.backend.utils;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 20:22
 */
public class NumberByteUtil {

    public static byte[] long48ToBytes(long l){
        byte[] result = new byte[6];
        for(int i = 5 ; i > 0 ;i--){
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }
}
