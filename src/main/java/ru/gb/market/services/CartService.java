package ru.gb.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.market.models.Product;
import ru.gb.market.repositories.CartRepository;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService (CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addProductToCart(Product product) {
        cartRepository.addProductToCart(product);
    }

    public void removeProductFromCart(Long id) {
        cartRepository.removeProductFromCart(id);
    }

//    public List<Product> getCartList() {
//        return cartRepository.getCartList();
//    }

    public List<Product> getCartList() {
        return cartRepository.getCartList();
    }

}
