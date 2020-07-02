package com.danbai.ys.async;

import com.danbai.ys.websocket.TvDmSocket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author danbai
 * @date 2019-11-04 21:36
 */
@Component
public class AsyncDmAllSend {
    /**
     * 群发自定义消息
     */
    @Async
    public void sendInfo(String message) throws IOException {
        for (TvDmSocket item : TvDmSocket.webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    @Async
    public void sendInfo(String message, String dmid) throws IOException {
        for (TvDmSocket item : TvDmSocket.webSocketSet) {
            if (item.getDmid().equals(dmid)) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    continue;
                }
            }
        }
    }

    @Async
    public void sendInfoNothis(String message, String dmid, TvDmSocket t) throws IOException {
        for (TvDmSocket item : TvDmSocket.webSocketSet) {
            if (item.getDmid().equals(dmid) && item != t) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    continue;
                }
            }
        }
    }
}
