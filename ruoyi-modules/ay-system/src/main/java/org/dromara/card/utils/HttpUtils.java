package org.dromara.card.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.util.TreeMap;

/**
 * @author Ay
 * 创建时间 2023-07-29
 */
public class HttpUtils {
    public static HttpResponse sendPost(String url, TreeMap body){
        return HttpRequest.post(url)
            .form(body)
            .execute();
    }
}
