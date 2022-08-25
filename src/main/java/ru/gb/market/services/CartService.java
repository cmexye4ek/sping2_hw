package ru.gb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.models.Cart;
import ru.gb.market.repositories.CartRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCart(String userId) {
        return cartRepository.getCartByUserId(userId);
    }

    public Cart addProductToCart(ProductDto productDto, String userId) {
        Cart cart = getCart(userId);
        List<ProductDto> list = cart.getProductsList();
        for (ProductDto pDto : list) {
            if (pDto.getId().equals(productDto.getId())) {
                pDto.setAmount(pDto.getAmount() + 1L);
                cart.setProductsList(list);
                cartRepository.updateCartInCache(cart);
                return cart;
            }
        }
        list.add(productDto);
        cart.setProductsList(list);
        cartRepository.updateCartInCache(cart);
        return cart;
    }

    public Cart decrementProductInCart(ProductDto productDto, String userId) {
        Cart cart = getCart(userId);
        List<ProductDto> list = cart.getProductsList();
        for (ProductDto pDto : list) {
            if (pDto.getId().equals(productDto.getId()) && pDto.getAmount() > 1) {
                pDto.setAmount(pDto.getAmount() - 1L);
                cart.setProductsList(list);
                cartRepository.updateCartInCache(cart);
                return cart;
            }
        }
        return removeFromCart(productDto.getId(), userId);
    }

    public Cart removeFromCart(Long productId, String userId) {
        Cart cart = getCart(userId);
        List<ProductDto> list = cart.getProductsList();
        list.removeIf(p -> p.getId().equals(productId));
        cart.setProductsList(list);
        cartRepository.updateCartInCache(cart);
        return cart;
    }

    public Cart mergeCarts(String guestUuid, String userId) {
        Cart guestCart = getCart(guestUuid);
        Cart userCart = getCart(userId);
        List<ProductDto> guestDtoList = guestCart.getProductsList();
        List<ProductDto> userDtoList = userCart.getProductsList();
        HashMap<Long, ProductDto> map = new HashMap<>();
        if (userDtoList.isEmpty()) {
            userCart.setProductsList(guestDtoList);
        } else {
            userDtoList.addAll(guestDtoList);
            for (ProductDto userDto : userDtoList) {
                if (!map.containsKey(userDto.getId())) {
                    map.put(userDto.getId(), userDto);
                } else {
                    userDto.setAmount(userDto.getAmount() + map.get(userDto.getId()).getAmount());
                    map.put(userDto.getId(), userDto);
                }
            }
            userCart.setProductsList(new ArrayList<>(map.values()));

        }
        cartRepository.updateCartInCache(userCart);
        cartRepository.removeCartFromCache(guestUuid);
        return userCart;
    }

//    public Cart checkout(Long userId) {
//
//        return null;
//    }

}
