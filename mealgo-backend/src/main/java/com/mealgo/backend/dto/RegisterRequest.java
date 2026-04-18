package com.mealgo.backend.dto;

import lombok.Data;

@Data //tu tao getter setter constructor voi Lombok
public class RegisterRequest {
    private String name;
    private String email;
    private String password; //nhan du lieu frontend gui
}
