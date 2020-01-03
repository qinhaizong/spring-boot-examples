package com.neo.cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 淘汰算法
 * FIFO：First In First Out，先进先出。 判断被存储的时间，离目前最远的数据优先被淘汰。
 * <p>
 * LRU：Least Recently Used，最近最少使用。 判断最近被使用的时间，目前最远的数据优先被淘汰。
 * <p>
 * LFU：Least Frequently Used，最不经常使用。 在一段时间内，数据被使用次数最少的，优先被淘汰。
 * <p>
 * LFU(最不经常使用)、LRU(最近最少使用)、ARC(自适应缓存替换算法)、FIFO(先进先出)、MRU(最近最常使用算法)
 */
@Slf4j
public class CachingEvictionAlgorithmTest {
    private int size = 5;

    @Test
    public void lru() {
        LinkedHashMap<String, Integer> cache = new LinkedHashMap<String, Integer>(size, .75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                boolean tooBig = size() > size;
                if (tooBig) {
                    log.info("removeEldestEntry {}", eldest);
                }
                return tooBig;
            }
        };
        IntStream.rangeClosed(1, size * 2).forEach(i -> cache.put(Character.toString((char) ('a' + i)), i));
        log.info("{}", cache);
        Assert.assertEquals(size, cache.size());
        cache.get("g");
        cache.put("z", 15);
        log.info("{}", cache);
        Assert.assertTrue(cache.containsKey("g"));
    }

    @Test
    public void fifo() {
        LinkedHashMap<String, Integer> cache = new LinkedHashMap<String, Integer>(size, .75F, false) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                boolean tooBig = size() > size;
                if (tooBig) {
                    log.info("removeEldestEntry {}", eldest);
                }
                return tooBig;
            }
        };
        IntStream.rangeClosed(1, size * 2).forEach(i -> cache.put(Character.toString((char) ('a' + i)), i));
        log.info("{}", cache);
        Assert.assertEquals(size, cache.size());
        cache.get("g");
        cache.put("z", 15);
        log.info("{}", cache);
        Assert.assertTrue(!cache.containsKey("g"));
    }
}
