package com.srdevepereira.pdv.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ResponseDTO <T>{

    @Getter
    private List<String> message;
    @Getter
    private T data;

    public ResponseDTO(List<String> message, T data) {
        this.message = this.message;
        this.data = data;
    }

    public ResponseDTO(String message, T data) {
        this.message = Arrays.asList(message);
        this.data = data;
    }

}
