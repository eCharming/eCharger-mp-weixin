package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Redis {

    @Autowired
    @Qualifier("template")
    private RedisTemplate redisTemplate;

    public boolean isExisted(String uid,String toUid){
        if(redisTemplate.hasKey(uid+"to"+toUid))
            return true;
        else return false;
    }

    public void setMessage(String uid, String toUid, String message) {
        redisTemplate.opsForList().rightPush(uid+"to"+toUid,message);
    }

    public List getMessage(String uid, String toUid) {
        if(isExisted(uid,toUid)){
            Long size=redisTemplate.opsForList().size(uid+"to"+toUid);
            List<String> message=new ArrayList<>();
            for(int i=0;i<size;i++){
                message.add((String) redisTemplate.opsForList().leftPop(uid+"to"+toUid));
            }
//            List message=redisTemplate.opsForList().rightPop(uid+"/"+toUid,size);
            return message;
        }else return null;
    }

    public void select(int index) {
        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        if (lettuceConnectionFactory != null) {
            lettuceConnectionFactory.setDatabase(index);
            redisTemplate.setConnectionFactory(lettuceConnectionFactory);
            lettuceConnectionFactory.resetConnection();
        }
    }
}
