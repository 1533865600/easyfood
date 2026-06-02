package fun.cyhgraph.controller;

import fun.cyhgraph.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class RedisTestController {

    @Autowired
    private RedisUtils redisUtils;

    private static final String SHOP_STATUS_KEY = "shop:status";

    // 改成 @GetMapping，浏览器直接访问就能用
    @GetMapping("/status/{status}")
    public Map<String, Object> setShopStatus(@PathVariable Integer status) {
        redisUtils.set(SHOP_STATUS_KEY, status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("msg", "设置成功");
        return result;
    }

    @GetMapping("/status")
    public Map<String, Object> getShopStatus() {
        Object status = redisUtils.get(SHOP_STATUS_KEY);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("data", status != null ? status : 0);
        return result;
    }
}