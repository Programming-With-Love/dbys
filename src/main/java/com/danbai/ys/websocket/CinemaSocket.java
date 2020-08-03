/**
 * @author DanBai
 * @create 2020-03-10 22:57
 * @desc 同步影院sk
 **/
package com.danbai.ys.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.CinemaRoom;
import com.danbai.ys.entity.Token;
import com.danbai.ys.entity.VideoTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/cinema/socket/{username}")
@Component
public class CinemaSocket {
    /**
     * 用户名
     */
    private String username;
    /**
     * 房间id 0为大厅
     */
    private int roomId = 0;
    /**
     * 连接池
     */
    public static ConcurrentHashMap<String, CinemaSocket> POOL = new ConcurrentHashMap<>();
    /**
     * 房间池
     */
    public static ConcurrentHashMap<Integer, CinemaRoom> ROOM_POOL = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Integer> DELETE_P00L = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private static Logger log = LoggerFactory.getLogger(CinemaSocket.class);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        if (DELETE_P00L.containsKey(username)) {
            this.roomId = DELETE_P00L.get(username);
            DELETE_P00L.remove(username);
        }
        //加入POOL中
        POOL.put(session.getId(), this);
        //断线重连加房间
        if (roomId != 0) {
            CinemaRoom room = ROOM_POOL.get(roomId);
            if (room != null) {
                ROOM_POOL.get(roomId).getSockets().add(session.getId());
            }
        }
        //在线数加1
        log.info("有新连接加入！当前在线人数为" + POOL.size());
        CinemaSocketManagement.info(session.getId());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //从POOL中删除
        CinemaSocket cinemaSocket = POOL.get(session.getId());
        if (cinemaSocket != null) {
            DELETE_P00L.put(cinemaSocket.getUsername(), cinemaSocket.roomId);
            if (cinemaSocket.roomId != 0) {
                CinemaSocketManagement.exitRoom(session.getId());
            }
        }
        POOL.remove(session.getId());
        log.info("有一连接关闭！当前在线人数为" + POOL.size());
        log.info("房间数" + ROOM_POOL.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String id = session.getId();
        CinemaSocket cinemaSocket = POOL.get(id);
        if (cinemaSocket != null) {
            try {
                JSONObject jsonObject = JSON.parseObject(message);
                String type = jsonObject.getString("type");
                if (jsonObject != null && type != null) {

                    switch (type) {
                        case "info":
                            CinemaSocketManagement.info(id);
                            break;
                        case "join":
                            CinemaSocketManagement.joinRoom(id, jsonObject.getInteger("roomId"), jsonObject.getString("pass"));
                            break;
                        case "newRoom":
                            CinemaSocketManagement.newRoom(id, jsonObject.getString("name"), jsonObject.getString("pass"));
                            break;
                        case "exitRoom":
                            CinemaSocketManagement.exitRoom(id);
                            break;
                        case "roomInfo":
                            CinemaSocketManagement.roomInfo(id);
                            break;
                        case "sendChat":
                            CinemaSocketManagement.sendChat(id, jsonObject.getString("msg"));
                            break;
                        case "sendUrl":
                            CinemaSocketManagement.sendUrl(id, jsonObject.getString("url"));
                            break;
                        case "sendTime":
                            CinemaSocketManagement.sendTime(id, jsonObject.getDouble("time"));
                            break;
                        case "transfer":
                            CinemaSocketManagement.transfer(id, jsonObject.getString("id"));
                            break;
                            //提交观看时间的减少http请求
                        case "postTime":
                            VideoTime videoTime = new VideoTime();
                            videoTime.setUsername(jsonObject.getString("username"));
                            videoTime.setYsid(jsonObject.getInteger("ysid"));
                            videoTime.setYsjiname(jsonObject.getString("ysjiname"));
                            videoTime.setTime(jsonObject.getFloat("time"));
                            Token token = new Token(jsonObject.getString("username"),jsonObject.getString("token"));
                            CinemaSocketManagement.postTime(videoTime,token);
                            break;
                        default:
                            log.info(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 发生错误时调用
     *
     * @param session session
     * @OnError 错误消息
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
     * @param message 消息
     */
    public void sendMessage(String message) {
        synchronized (session) {
            if (session.isOpen()) {
                this.session.getAsyncRemote().sendText(message);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void examine() {
        DELETE_P00L.clear();
        //删除断开的链接
        POOL.forEach((id, e) -> {
            if (!e.session.isOpen()) {
                POOL.remove(id);
            }
        });
        ROOM_POOL.forEach((id, room) -> {
            if (POOL.get(room.getAuthorId()) == null) {
                if (room.getSockets().size() < 2) {
                    ROOM_POOL.remove(id);
                } else {
                    //新房主
                    String newId = CinemaSocket.ROOM_POOL.get(id).getSockets().iterator().next();
                    //转让
                    if (newId != null) {
                        CinemaSocket.ROOM_POOL.get(roomId).setAuthorId(newId);
                    }
                }
            }
        });
    }
}
