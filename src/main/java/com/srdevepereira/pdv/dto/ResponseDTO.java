package com.srdevepereira.pdv.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ResponseDTO <T>{

    @Getter
    private List<String> message;

    public ResponseDTO(List<String> message) {
        this.message = this.message;
    }

    public ResponseDTO(String message) {
        this.message = Arrays.asList(message);
    }

}
