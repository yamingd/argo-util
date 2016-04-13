package com.argo.util.json;

import com.alibaba.fastjson.JSON;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json的工具类
 *
 */
public class JsonUtil {

    protected Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    protected static MessagePack messagePack;

    static {
        messagePack = new MessagePack();
    }

    /**
     * 转换JSON为List
     * @param json JSON格式的字符串
     * @param itemType 目标类型
     * @param <T> 目标类型
     * @return List
     * @throws Exception 抛出转换异常
     */
    public static <T> List<T> asList(String json, final T[] itemType)throws Exception {
        T[] temp = (T[]) JSON.parseObject(json, itemType.getClass());
        return Arrays.asList(temp);
    }

    /**
     * 转换二进制JSON为List
     * @param json JSON格式的字符串
     * @param itemType 目标类型
     * @param <T> 目标类型
     * @return List
     * @throws Exception 抛出转换异常
     */
    public static <T> List<T> asList(byte[] json, final T[] itemType)throws Exception {
        T[] temp = (T[])messagePack.read(json, itemType.getClass());
        return Arrays.asList(temp);
    }
    /**
     *
     * @param clazz 目标类型
     * @param json JSON格式的字符串
     * @param <T> 目标类型
     * @return T 目标类型实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T asT(Class<?> clazz, String json) {
        if (json == null) return null;
        return (T) JSON.parseObject(json, clazz);
    }

    /**
     * 从二进制JSON转为对象
     * @param clazz 目标类型
     * @param json JSON格式的字符串
     * @param <T> 目标类型
     * @return T
     * @throws IOException 抛出转换异常
     */
    public static <T> T asT(Class<?> clazz, byte[] json) throws IOException {
        if (json == null || json.length == 0)
            return null;
        return (T) messagePack.read(json, clazz);
    }

    /**
     * 将一个对象转成json字符串
     *
     * @param obj Java对象
     * @return String
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 转为二进制JSON.
     * @param obj Java对象
     * @param <T> 目标类型
     * @return byte[]
     * @throws Exception 抛出转换异常
     */
    public static <T> byte[] toBytes(T obj) throws Exception {
        if (obj == null){
            throw new IllegalArgumentException("obj is NULL.");
        }
        return messagePack.write(obj);
    }

    /**
     *
     * @param json JSON格式的字符串
     * @return Map
     */
    public static Map<String, Object> asMap(String json) {
        if (json == null) return null;
        return JSON.parseObject(json, HashMap.class);
    }

    /**
     *
     * @param json 二进制数据
     * @return HashMap
     * @throws IOException 抛出转换异常
     */
    public static HashMap asMap(byte[] json) throws IOException {
        if (json == null)
            return null;
        return messagePack.read(json, HashMap.class);
    }
}
