package ru.gb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.mappers.ProductMapper;
import ru.gb.market.services.ProductService;


@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final ProductMapper productMapper;

    //http://localhost:8189/market/api/v1/products
    @GetMapping
    public Page<ProductDto> findAll(@RequestParam(name = "page", defaultValue = "1") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return productService.findAll(pageIndex - 1, 10).map(productMapper::mapEntityToDto);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id).map(productMapper::mapEntityToDto).orElseThrow(() -> new ResourceNotFoundException("Product id = " + id + "not found on market"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewProduct(@RequestBody ProductDto productDto) {
        productService.save(productMapper.mapDtoToEntity(productDto));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.save(productMapper.mapDtoToEntity(productDto));
    }

    @DeleteMapping("/{id}")
    public int deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return HttpStatus.OK.value();
    }
}
