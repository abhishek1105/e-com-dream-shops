package com.freeCodeCamp.dream_shops.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
}
