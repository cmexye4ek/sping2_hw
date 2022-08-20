package ru.gb.market.exceptions;

import java.util.Date;

public class ProductErrorResponce {
    private String message;
    private Date date;

    public ProductErrorResponce(String message) {
        this.message = message;
        this.date = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
