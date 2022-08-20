package ru.gb.market.repositories;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.gb.market.models.Category;
import ru.gb.market.models.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CartRepository {
    private List<Product> cart;

    @PostConstruct
    public void init() {
        this.cart = new ArrayList<>();
    }

    public void addProductToCart(Product product) {
        cart.add(product);
    }

    public void removeProductFromCart(Long id) {
        cart.removeIf(p -> p.getId() == id);
    }

    public List<Product> getCartList() {
        return cart;
    }
}
