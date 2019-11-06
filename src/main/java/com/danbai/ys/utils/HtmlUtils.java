package com.danbai.ys.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author danbai
 * @date 2019-10-14 19:56
 */
public class HtmlUtils {
    /**
     * 获取url网页的源代码
     *
     * @param urlStr 链接
     * @return 网页源代码
     */
    private static String ip = null;
    private static int port = 0;

    public static String getHtmlContent(String urlStr) {
        if (ip == null) {
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

    public static void upipdl() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://118.24.52.95/get/")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = JSONObject.parseObject(response.body().string());
            String proxy = jsonObject.getString("proxy");
            String[] temp1;
            temp1 = proxy.split(":");
            ip = temp1[0];
            port = Integer.parseInt(temp1[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getHtmlContentNp(String urlStr) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
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
        return getHtmlContent(urlStr);
    }
    public static String getUrlfile(String strUrl)
    {
        InputStream in = null;
        OutputStream out = null;
        String strdata = "";
        try
        {
            URL url = new URL(strUrl);
            // 创建 URL
            in = url.openStream();
            // 打开到这个URL的流
            out = System.out;
            // 复制字节到输出流
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1)
            {
                String reads = new String(buffer, 0, bytesRead, "UTF-8");
                strdata = strdata + reads;
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            out.close();
            return strdata;
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.err.println("Usage: java GetURL <URL> [<filename>]");
            return strdata;
        }
    }
}
