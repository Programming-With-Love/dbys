package com.danbai.ys.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author danbai
 * @date 2019-10-14 19:56
 */
public class HtmlUtils {
    /**
     * 获取url网页的源代码
     * @param urlStr 链接
     * @return 网页源代码
     */
    private static String ip=null;
    private static int port=0;
    public static String getHtmlContent(String urlStr) {
        if (ip==null){
            upipdl();
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)))
                .build();
        Request request = new Request.Builder()
                .url(urlStr)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            System.out.println(e);
        }
        upipdl();
        return getHtmlContent(urlStr);
    }
    public static void upipdl(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://118.24.52.95/get/")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = JSONObject.parseObject(response.body().string());
            String proxy=jsonObject.getString("proxy");
            String[] temp1;
            temp1 = proxy.split(":");
            ip=temp1[0];
            port= Integer.parseInt(temp1[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
