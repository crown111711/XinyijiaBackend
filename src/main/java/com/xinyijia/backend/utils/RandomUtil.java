package com.xinyijia.backend.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 17:00
 */
@Slf4j
public class RandomUtil {

    private static final long CUSTOM_EPOCH = LocalDateTime.of(2018, 2, 1, 0, 0, 0, 0).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    ;

    private static Object seqLock = new Object();
    private static byte sequence = 0;
    private static long referenceTime = System.currentTimeMillis();

    public static String random(int bit) {
        Random random = new Random();
        StringBuilder resultStr = new StringBuilder();
        for (int i = 0; i < bit; i++) {
            int count = random.nextInt(10);
            resultStr.append(count);
        }
        return resultStr.toString();
    }


    public static long nextSeq() {
        long currentTime = System.currentTimeMillis();
        long counter;
        synchronized (seqLock) {
            if (currentTime < referenceTime) {
                log.warn("上一次生成在这次生成之后");
            } else if (currentTime > referenceTime) {
                sequence = 0;
            } else {
                sequence++;
            }
            counter = sequence;
            referenceTime = currentTime;
        }

        System.out.println((currentTime - CUSTOM_EPOCH) << 8| counter);

        return (currentTime - CUSTOM_EPOCH) << 8 | counter;
    }

    /**
     * globalToken采用48位大小，中间40位是时间，最后8位是同一时间的序列号,为了解决分布式session问题
     * @return
     */
    public static String getGlobalToken() {
        long next = nextSeq();
        byte[] result = NumberByteUtil.long48ToBytes(next);
        return  HexStringUtil.bytesToHex(result);
    }

}
