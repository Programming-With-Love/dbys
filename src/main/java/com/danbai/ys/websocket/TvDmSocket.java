package com.danbai.ys.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.async.AsyncDmAllSend;
import com.danbai.ys.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author danbai
 * @date 2019-11-04 19:50
 */
@ServerEndpoint("/tvdm")
@Component
public class TvDmSocket {
    private String dmid;
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    public static CopyOnWriteArraySet<TvDmSocket> webSocketSet = new CopyOnWriteArraySet<TvDmSocket>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private static Logger log = LoggerFactory.getLogger(TvDmSocket.class);

    public String getDmid() {
        return dmid;
    }

    public void setDmid(String dmid) {
        this.dmid = dmid;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        //加入set中
        addOnlineCount();
        //在线数加1
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        //从set中删除
        subOnlineCount();
        //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        this.session = session;
        log.info(message);
        JSONObject jsonObject = JSON.parseObject(message);
        String type = jsonObject.getString("type");
        if (type == null) {
            return;
        }
        try {
            switch (type) {
                case "setdmid":
                    this.setDmid(jsonObject.getString("data"));
                    break;
                case "send":
                    if (this.getDmid() == null) {
                        this.sendMessage("先设置dmid");
                    }
                    SpringUtil.getBean(AsyncDmAllSend.class).sendInfoNothis(jsonObject.getJSONObject("data").toJSONString(),
                            this.dmid, this);
                    break;
                default:
                    this.sendMessage(message);
            }
        } catch (IOException e) {
            log.error("onMessage方法异常" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     *
     * @OnError
     **/
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("onMessage方法异常" + error.toString());
        error.printStackTrace();
    }


    /**
     * 发送消息需注意方法加锁synchronized，避免阻塞报错
     * 注意session.getBasicRemote()与session.getAsyncRemote()的区别
     *
     * @param message
     * @throws IOException
     */
    public synchronized void sendMessage(String message) throws IOException {
        this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (TvDmSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        TvDmSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        TvDmSocket.onlineCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TvDmSocket that = (TvDmSocket) o;
        return Objects.equals(dmid, that.dmid) &&
                Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dmid, session);
    }
}
