package ru.gb.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String title;
    private Double cost;

    private Long amount;

    private Double summaryCost;

    private String categoryTitle;

//    public ProductDto(Product product) {
//        this.id = product.getId();
//        this.title = product.getTitle();
//        this.cost = product.getCost();
//        this.categoryTitle = product.getCategory().getTitle();
//    }

    public Double getSummaryCost() {
        if (amount != null) {
            return amount * cost;
        }
        return cost;
    }

}
