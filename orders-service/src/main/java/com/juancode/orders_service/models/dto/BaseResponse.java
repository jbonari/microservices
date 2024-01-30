package com.juancode.orders_service.models.dto;

public record BaseResponse(String[] errorMessages) {

    public boolean hasErrors(){
        return errorMessages !=null && errorMessages.length>0;
    }
}
