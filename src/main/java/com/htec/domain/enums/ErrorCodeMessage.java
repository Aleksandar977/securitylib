package com.htec.domain.enums;


import lombok.Getter;

@Getter
public enum ErrorCodeMessage {

    NOT_AUTHORIZED("err004"),
    INVALID_TOKEN("err009");

    private final String message;

    ErrorCodeMessage(String message) {
        this.message = message;
    }

}
