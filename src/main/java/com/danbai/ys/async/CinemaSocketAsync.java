/**
 * @author DanBai
 * @create 2020-03-17 16:29
 * @desc yb
 **/
package com.danbai.ys.async;

import com.danbai.ys.websocket.CinemaSocket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CinemaSocketAsync {
    @Async
    public  void sendRoomMsg(int id,String msg){
        CinemaSocket.ROOM_POOL.get(id).getSockets().forEach(sid->{
            CinemaSocket.POOL.get(sid).sendMessage(msg);
        });
    }
    @Async
    public  void sendRoomMsgPassAuthor(int id,String msg){
        String authorId = CinemaSocket.ROOM_POOL.get(id).getAuthorId();
        CinemaSocket.ROOM_POOL.get(id).getSockets().forEach(sid->{
            if(!sid.equals(authorId)){
                CinemaSocket.POOL.get(sid).sendMessage(msg);
            }
        });
    }
}
