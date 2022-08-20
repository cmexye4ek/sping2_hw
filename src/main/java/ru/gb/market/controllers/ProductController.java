package ru.gb.market.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.models.Category;
import ru.gb.market.models.Product;
import ru.gb.market.services.CategoryService;
import ru.gb.market.services.ProductService;

import javax.print.attribute.standard.PrinterMessageFromOperator;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    //http://localhost:8189/market/api/v1/products
    @GetMapping
    public Page<ProductDto> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return productService.findAll(pageIndex - 1, 10).map(ProductDto::new);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id).map(ProductDto::new).orElseThrow(() -> new ResourceNotFoundException("Product id = " + id + "not found on market"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category title = "+ productDto.getCategoryTitle() +" not found on market"));
        product.setCategory(category);
        productService.save(product);
        return new ProductDto(product);
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.updateProductFromDto(productDto);
    }

    @DeleteMapping("/{id}")
    public int deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return HttpStatus.OK.value();
    }
}
