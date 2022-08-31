package ru.gb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.market.models.Product;
import ru.gb.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> findAll(int pageIndex, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageIndex, pageSize, Sort.by("id")));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

//    @Transactional
//    public void updateProductFromDto (ProductDto productDto) {
//        Product product = findById(productDto.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Product id = " + productDto.getId() + "not found on market"));
//        product.setTitle(productDto.getTitle());
//        product.setCost(productDto.getCost());
//        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
//                .orElseThrow(() -> new ResourceNotFoundException("Category title = "+ productDto.getCategoryTitle() +" not found on market"));
//        product.setCategory(category);
//    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
