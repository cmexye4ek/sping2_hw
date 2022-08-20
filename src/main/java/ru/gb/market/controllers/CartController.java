package ru.gb.market.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.models.Category;
import ru.gb.market.models.Product;
import ru.gb.market.services.CartService;
import ru.gb.market.services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CategoryService categoryService;

    public CartController(CartService cartService, CategoryService categoryService) {
        this.cartService = cartService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ProductDto> getCartList() {
        return cartService.getCartList().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProductToCart(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category title = " + productDto.getCategoryTitle() + " not found on market"));
        product.setCategory(category);
        cartService.addProductToCart(product);
        return new ProductDto(product);
    }


    @DeleteMapping("/{id}")
    public int deleteFromCart(@PathVariable Long id) {
        cartService.removeProductFromCart(id);
        return HttpStatus.OK.value();
    }
}
