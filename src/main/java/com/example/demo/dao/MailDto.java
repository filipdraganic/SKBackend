package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
public class MailDto {

    private String to;

    private String title;

    private String body;


    public MailDto(String to, String title, String body) {
        this.to = to;
        this.title = title;
        this.body = body;

    }
}
