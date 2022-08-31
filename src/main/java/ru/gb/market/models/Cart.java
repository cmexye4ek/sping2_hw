package ru.gb.market.models;

import lombok.Data;
import ru.gb.market.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;


@Data
public class Cart {

    private String userId;
    private List<ProductDto> productsList;

    public Cart() {
        this.productsList = new ArrayList<>();
    }

}
