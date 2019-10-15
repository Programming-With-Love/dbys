package com.danbai.ys.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author danbai
 * @date 2019-10-14 19:56
 */
public class HtmlUtils {
    /**
     * 获取url网页的源代码
     * @param urlStr 链接
     * @param charset 网页的编码格式
     * @return 网页源代码
     */
    static String HTTP="http://";
    static int ERR=400;
    public static String getHtmlContent(String urlStr, String charset) {
        //检查urlStr头部格式是否正确
        if (!urlStr.toLowerCase().startsWith("HTTP")) {
            urlStr = "http://" + urlStr;
        }

        URL url=null;
        StringBuffer contentBuffer = new StringBuffer();
        //内容源代码
        int responseCode = -1;
        //网页返回信息吗
        HttpURLConnection con = null;

        try {
            url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // IE代理进行下载
            con.setConnectTimeout(60000);
            //连接超时设置为60s即一分钟
            con.setReadTimeout(60000);
            //con.setDoOutput(true);
            // 获得网页返回信息码
            responseCode = con.getResponseCode();
            if (responseCode == -1) {
                System.out.println(url.toString() + " : connection is failure...");
                con.disconnect();
                return null;
            }
            if (responseCode >= ERR)
                // 请求失败
            {
                System.out.println("请求失败:get response code: " + responseCode);
                con.disconnect();
                return null;
            }
            //获取网页源代码
            InputStream in = con.getInputStream();
            //InputStream in = url.openStream();
            InputStreamReader is = new InputStreamReader(in, charset);
            BufferedReader br = new BufferedReader(is);

            String str = null;
            while((str = br.readLine()) != null) {
                contentBuffer.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            contentBuffer = null;
            System.out.println("error: " + url.toString());
        }finally {
            con.disconnect();
        }
        return contentBuffer.toString();
    }

    /**
     * 保存HTML内容
     * @param content HTML源代码内容
     * @param charset 网页编码格式
     * @param savePath 保存路径
     */
    public static void saveHtmlContent(String content,String charset,String savePath){
        try {
            FileOutputStream fout = new FileOutputStream(savePath);
            OutputStreamWriter os = new OutputStreamWriter(fout, charset);
            BufferedWriter bw=new BufferedWriter(os);
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
