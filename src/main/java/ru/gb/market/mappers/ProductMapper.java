package ru.gb.market.mappers;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.models.Category;
import ru.gb.market.models.Product;
import ru.gb.market.services.CategoryService;

@Component
@Data
public class ProductMapper {

    private final CategoryService categoryService;

    public ProductDto mapEntityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setCost(product.getCost());
        productDto.setAmount(1L);
        productDto.setSummaryCost(product.getCost());
        if (product.getCategory() != null) {
            productDto.setCategoryTitle(product.getCategory().getTitle());
        }
        return productDto;
    }

    public Product mapDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        if (productDto.getCategoryTitle() != null) {
            categoryService.findByTitle(productDto.getCategoryTitle())
                    .ifPresentOrElse(
                            product::setCategory, () -> {
                                Category newCategory = categoryService.create(productDto.getCategoryTitle());
                                product.setCategory(newCategory);
                            }
                    );
        }
        return product;
    }

}
