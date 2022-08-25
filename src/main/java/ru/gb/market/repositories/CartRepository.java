package ru.gb.market.repositories;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ru.gb.market.models.Cart;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartRepository {

    private List<Cart> carts;
    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        this.carts = new ArrayList<>();
    }

    public Cart getCartByUserId(String userId) {
        if (!redisTemplate.hasKey(userId)) {
            Cart cart = new Cart();
            cart.setUserId(userId);
            redisTemplate.opsForValue().set(userId, cart);
        }
        return (Cart) redisTemplate.opsForValue().get(userId);
    }

    public void updateCartInCache(Cart cart) {
        redisTemplate.opsForValue().set(cart.getUserId(), cart);
    }

    public void removeCartFromCache(String userId) {
        redisTemplate.delete(userId);
    }

}
