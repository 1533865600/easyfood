package fun.cyhgraph.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存字符串
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 存字符串并设置过期时间（秒）
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // 取字符串
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除 key
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}