package com.radioson.mms.socket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint(value = "/ws/{userId}")
public class WebSocketServer {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /** concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     在外部可以获取此连接的所有websocket对象，并能对其触发消息发送功能，我们的定时发送核心功能的实现在与此变量 */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        int count = onlineCount.incrementAndGet();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + count);
        try {
//            sendMessage("连接已建立成功.");
            sendMessage(sdf.format(new Date())+"|0.0|0.0|0.0|0.0|0.0|0.0|0.0");
        } catch (Exception e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     *
     * 参考dwrsession摧毁方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //连接关闭后，将此websocket从set中删除
        int count = onlineCount.decrementAndGet();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + count);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
    }

    // 错误提示
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    // 发送消息，在定时任务中会调用此方法           
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(CopyOnWriteArraySet<WebSocketServer> webSocketSet) {
        WebSocketServer.webSocketSet = webSocketSet;
    }
}
