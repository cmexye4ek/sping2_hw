package ru.gb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.dto.GuestUuid;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.models.Cart;
import ru.gb.market.services.CartService;


import java.security.Principal;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Cart getCart(@RequestParam(value = "uuid", required = false) GuestUuid guestUuid, Principal principal) {
        if (principal == null) {
            return cartService.getCart(guestUuid.getUuid());
        }
        return cartService.getCart(principal.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addToCart(@RequestBody ProductDto productDto, @RequestParam(value = "uuid", required = false) GuestUuid guestUuid, Principal principal) {
        if (principal == null) {
            return cartService.addProductToCart(productDto, guestUuid.getUuid());
        }
        return cartService.addProductToCart(productDto, principal.getName());
    }

    @PostMapping("/merge")
    @ResponseStatus(HttpStatus.OK)
    public Cart mergeCarts(@RequestBody GuestUuid guestUuid, Principal principal) {
        return cartService.mergeCarts(guestUuid.getUuid(), principal.getName());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Cart decrementProductInCart(@RequestBody ProductDto productDto, @RequestParam(value = "uuid", required = false) GuestUuid guestUuid, Principal principal) {
        if (principal == null) {
            return cartService.decrementProductInCart(productDto, guestUuid.getUuid());
        }
        return cartService.decrementProductInCart(productDto, principal.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cart deleteFromCart(@PathVariable Long id, @RequestParam(value = "uuid", required = false) GuestUuid guestUuid, Principal principal) {
        if (principal == null) {
            return cartService.removeFromCart(id, guestUuid.getUuid());
        }
        return cartService.removeFromCart(id, principal.getName());
    }
}




