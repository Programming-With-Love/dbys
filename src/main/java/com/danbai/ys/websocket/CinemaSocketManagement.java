/**
 * @author DanBai
 * @create 2020-03-11 0:22
 * @desc socket管理
 **/
package com.danbai.ys.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.async.CinemaSocketAsync;
import com.danbai.ys.entity.CinemaRoom;
import com.danbai.ys.entity.Token;
import com.danbai.ys.entity.VideoTime;
import com.danbai.ys.service.impl.UserServiceImpl;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.Md5;
import com.danbai.ys.utils.SpringUtil;

import io.agora.media.RtcTokenBuilder;
import io.agora.sample.RtcTokenBuilderSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CinemaSocketManagement {
    /**
     * 加入房间
     *
     * @param socketId socketId
     * @param roomId   房间id
     * @param pass     房间密码
     */
    public static void joinRoom(String socketId, int roomId, String pass) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "join");
        boolean ok = false;
        CinemaRoom cinemaRoom = CinemaSocket.ROOM_POOL.get(roomId);
        //是否需要密码
        if (cinemaRoom != null && cinemaRoom.getPass() != null) {
            if (cinemaRoom.getPass().equals(pass)) {
                //密码正确
                CinemaSocket.ROOM_POOL.get(roomId).getSockets().add(socketId);
                CinemaSocket.POOL.get(socketId).setRoomId(roomId);
                ok = true;
            }
        } else {
            CinemaSocket.ROOM_POOL.get(roomId).getSockets().add(socketId);
            CinemaSocket.POOL.get(socketId).setRoomId(roomId);
            ok = true;
        }
        jsonObject.put("ok", ok);
        if (ok) {
            //语音token
            RtcTokenBuilder token = new RtcTokenBuilder();
            int timestamp = (int) (System.currentTimeMillis() / 1000 + 3600);
            String result = token.buildTokenWithUid(RtcTokenBuilderSample.appId, RtcTokenBuilderSample.appCertificate,
                    Md5.getMD5LowerCase(cinemaRoom.getName() + cinemaRoom.getId()), Integer.parseInt(socketId, 16), RtcTokenBuilder.Role.Role_Publisher, timestamp);
            jsonObject.put("token", result);
            jsonObject.put("channel", Md5.getMD5LowerCase(cinemaRoom.getName() + cinemaRoom.getId()));
            jsonObject.put("id", String.valueOf(cinemaRoom.getId()));
            jsonObject.put("name", cinemaRoom.getName());
            jsonObject.put("uid", Integer.parseInt(socketId, 16));
        }
        CinemaSocket.POOL.get(socketId).sendMessage(jsonObject.toJSONString());
    }

    /**
     * 创建房间
     *
     * @param socketId socketId
     * @param name     房间名字
     * @param pass     密码
     */
    public static void newRoom(String socketId, String name, String pass) {
        if (name != null) {
            int id = CinemaSocket.ROOM_POOL.size() + 1;
            if (pass.equals("")) {
                pass = null;
            }
            CinemaRoom cinemaRoom = new CinemaRoom(id, name, pass, socketId);
            CinemaSocket.ROOM_POOL.put(cinemaRoom.getId(), cinemaRoom);
            joinRoom(socketId, id, pass);
        }
    }

    /**
     * 发送大厅消息
     *
     * @param msg 消息
     */
    public static void sendLobby(String msg) {
        CinemaSocket.POOL.forEach((id, socket) -> {
            if (socket.getRoomId() == 0) {
                socket.sendMessage(msg);
            }
        });
    }

    /**
     * 退出房间
     *
     * @param socketId socketId
     */
    public static void exitRoom(String socketId) {
        int roomId = CinemaSocket.POOL.get(socketId).getRoomId();
        //房主判断
        CinemaRoom room = CinemaSocket.ROOM_POOL.get(roomId);
        if (room != null) {
            if (room.getAuthorId().equals(socketId)) {
                System.out.println("是房主");
                //大于1人转让房主
                if (room.getSockets().size() > 1) {
                    System.out.println("转让");
                    //退出房间
                    CinemaSocket.ROOM_POOL.get(roomId).getSockets().remove(socketId);
                    //新房主
                    String newId = CinemaSocket.ROOM_POOL.get(roomId).getSockets().iterator().next();
                    //转让
                    if (newId != null) {
                        CinemaSocket.ROOM_POOL.get(roomId).setAuthorId(newId);
                    }

                } else {
                    //删除房间
                    CinemaSocket.ROOM_POOL.remove(roomId);
                }
            } else {
                //退出
                System.out.println("房客退出");
                CinemaSocket.ROOM_POOL.get(roomId).getSockets().remove(socketId);
            }
        }
    }

    /**
     * 获取大厅房间信息
     *
     * @param socketId socketId
     * @return
     */
    public static void info(String socketId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "info");
        //在线人数
        jsonObject.put("online", CinemaSocket.POOL.size());
        //遍历添加房间信息
        JSONArray rooms = new JSONArray();
        CinemaSocket.ROOM_POOL.forEach((id, room) -> {
            JSONObject roomJson = new JSONObject();
            roomJson.put("id", id);
            roomJson.put("name", room.getName());
            roomJson.put("online", room.getSockets().size());
            roomJson.put("author", CinemaSocket.POOL.get(room.getAuthorId()).getUsername());
            roomJson.put("needPass", room.getPass() == null ? false : true);
            rooms.add(roomJson);
        });
        jsonObject.put("rooms", rooms);
        CinemaSocket.POOL.get(socketId).sendMessage(jsonObject.toJSONString());

    }

    /**
     * 房间信息
     *
     * @param socketId
     */
    public static void roomInfo(String socketId) {
        JSONObject roomJson = new JSONObject();
        roomJson.put("type", "roomInfo");
        CinemaSocket cinemaSocket = CinemaSocket.POOL.get(socketId);
        if(cinemaSocket==null){
            return;
        }
        CinemaRoom room = CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId());
        if(room==null){
            return;
        }
        roomJson.put("id", room.getId());
        roomJson.put("name", room.getName());
        roomJson.put("online", room.getSockets().size());
        roomJson.put("url", room.getUrl());
        roomJson.put("time", room.getTime());
        roomJson.put("author", CinemaSocket.POOL.get(room.getAuthorId()).getUsername());
        JSONArray users = new JSONArray();
        room.getSockets().forEach(id -> {
            JSONObject user = new JSONObject();
            user.put("id", Integer.parseInt(id, 16));
            user.put("username", CinemaSocket.POOL.get(id).getUsername());
            users.add(user);
        });
        roomJson.put("users", users);

        CinemaSocket.POOL.get(socketId).sendMessage(roomJson.toJSONString());
    }

    public static void sendChat(String socketId, String msg) {
        CinemaSocket cinemaSocket = CinemaSocket.POOL.get(socketId);
        if(cinemaSocket==null){
            return;
        }
        CinemaRoom room = CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId());
        if(room==null){
            return;
        }
        JSONObject sendMsg = new JSONObject();
        sendMsg.put("type", "sendChat");
        sendMsg.put("id", Integer.parseInt(socketId, 16));
        sendMsg.put("roomId", room.getId());
        sendMsg.put("username", cinemaSocket.getUsername());
        sendMsg.put("msg", msg);
        SpringUtil.getBean(CinemaSocketAsync.class).sendRoomMsg(room.getId(), sendMsg.toJSONString());
    }

    public static void sendUrl(String socketId, String url) {
        CinemaSocket cinemaSocket = CinemaSocket.POOL.get(socketId);
        if(cinemaSocket==null){
            return;
        }
        CinemaRoom room = CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId());
        if(room==null){
            return;
        }
        if (room.getAuthorId().equals(socketId)) {
            CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId()).setUrl(url);
            JSONObject sendMsg = new JSONObject();
            sendMsg.put("type", "sendUrl");
            sendMsg.put("url", url);
            SpringUtil.getBean(CinemaSocketAsync.class).sendRoomMsgPassAuthor(room.getId(), sendMsg.toJSONString());
        }
    }

    public static void sendTime(String socketId, double time) {
        CinemaSocket cinemaSocket = CinemaSocket.POOL.get(socketId);
        if(cinemaSocket==null){
            return;
        }
        CinemaRoom room = CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId());
        if(room==null){
            return;
        }
        if (room.getAuthorId().equals(socketId)) {
            CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId()).setTime(time);
            JSONObject sendMsg = new JSONObject();
            sendMsg.put("type", "sendTime");
            sendMsg.put("time", time);
            SpringUtil.getBean(CinemaSocketAsync.class).sendRoomMsgPassAuthor(room.getId(), sendMsg.toJSONString());
        }
    }

    public static void transfer(String socketId, String transferId) {
        CinemaSocket cinemaSocket = CinemaSocket.POOL.get(socketId);
        if(cinemaSocket==null){
            return;
        }
        CinemaRoom room = CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId());
        if(room==null){
            return;
        }
        if (room.getAuthorId().equals(socketId)) {
            CinemaSocket.ROOM_POOL.get(cinemaSocket.getRoomId()).setAuthorId(Integer.toHexString(Integer.valueOf(transferId)));
        }
    }

    public static void postTime(VideoTime videoTime, Token token){
        //与一起看无关  观看历史同步
        if(SpringUtil.getBean(UserServiceImpl.class).checkToken(token)){
            SpringUtil.getBean(YsServiceImpl.class).addYsTime(videoTime);
        }
    }
}
