package ru.gb.market.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ProductErrorResponce {
    private String message;
    private Date date;

    public ProductErrorResponce(String message) {
        this.message = message;
        this.date = new Date();
    }

}
