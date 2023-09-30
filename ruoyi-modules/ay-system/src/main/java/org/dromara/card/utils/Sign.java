package org.dromara.card.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;

import java.util.TreeMap;

/**
 * @author Ay
 * 创建时间 2023-07-29
 */
public class Sign {

    private static final String PID = "3145";

    private static final String KEY = "PgK7WKILwH5gB0P9LG5K85liiWBKPHWk";

    /**
     * 获取签名
     * @param
     * @return
     */
    public static String getSign(TreeMap<String, Object> map){
        String sortStr = MapUtil.sortJoin(map, "&", "=", true, null);
        return SecureUtil.md5(sortStr + KEY);
    }
}
