package com.example.demo.service;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputChannel {

    String CHANNEL_NAME = "simple_channel";

    @Output(CHANNEL_NAME)
    MessageChannel output();

}
