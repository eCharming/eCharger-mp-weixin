package com.example.demo.Controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.POJO.Message;
import com.example.demo.Service.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocketapi/{uid}/{toUid}")
@Component
public class WebSocketServerEndpoint {


    private static Redis redis;

    @Autowired
    public void setRedis(Redis redis){
        WebSocketServerEndpoint.redis=redis;
    }

    //线程安全的哈希表，用以存储所有同时在连接的用户的连接通道和会话连接
    private static ConcurrentHashMap<String,Connection> sessionMap=new ConcurrentHashMap<String,Connection>();

    //一位用户到另一位用户的连接通道
    private class Connection {
        private String toUid="";
        private Session session;

        public Connection() {};

        public Connection(String toUid, Session session) {
            this.toUid = toUid;
            this.session = session;
        }

        public void setToUid(String toUid) {
            this.toUid = toUid;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public String getToUid() {
            return toUid;
        }

        public Session getSession() {
            return session;
        }
    };



    private String uid="";//存储用户的uid
    private String toUid="";//存储用户准备发到的uid
    private Connection connection=new Connection();
    private Session session;//存储用户的临时会话链接



    @OnOpen//链接打开
    public void onOpen(Session session, @PathParam("uid") String uid, @PathParam("toUid") String toUid) throws IOException {
        redis.select(1);
        this.uid=uid;
        this.toUid=toUid;
        this.session=session;
        connection.setToUid(toUid);
        connection.setSession(session);
        sessionMap.put(uid,connection);
        List<String> messages=redis.getMessage(toUid,uid);//查看另一位客户是否有发给我的消息
        if(messages!=null){
            for (String message:messages) {
                session.getBasicRemote().sendText(message);
            }
        }

        session.getBasicRemote().sendText("{\"message\":\"服务器回复的消息: 连接成功\"}" );
    }

    @OnClose
    public void onClose(){
        sessionMap.remove(uid);
    }

    @OnMessage//从用户端接收信息并且转发给另一位用户
    public void onMessage(Session session, String message) throws IOException {
        String revUid=toUid;                                  //反向通道
        session.getBasicRemote().sendText("{\"message\":\"服务器回复的消息: 发送中\"}" );

        

        if (sessionMap.containsKey(revUid)){           //查询另一位客户是否存在连接
            Connection revConnection=sessionMap.get(revUid); //反向连接
            if(revConnection.getToUid().equals(uid)){  //如果另一位客户连接到此客户
                Session revSession=revConnection.getSession();
                revSession.getBasicRemote().sendText(message);
            }else{
                redis.setMessage(uid,toUid,message);
                session.getBasicRemote().sendText("{\"message\":\"服务器回复的消息:发送失败\"}");
            }
        }else{
            redis.setMessage(uid,toUid,message);
            session.getBasicRemote().sendText("{\"message\":\"服务器回复的消息:发送失败\"}");
        }
    }
}