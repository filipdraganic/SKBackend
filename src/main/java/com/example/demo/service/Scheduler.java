package com.example.demo.service;

import com.example.demo.dao.MailDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableBinding(OutputChannel.class)
@RequiredArgsConstructor

public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger("SchedulerService");

    private final OutputChannel outputChannel;

   // @Scheduled(fixedDelay = 10000)
    public void publish(){
        MailDto mailDto = new MailDto("skserveremail@gmail.com",  "Zdravo", "Zdravo zdravo");
        outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
        logger.info("Hello");
    }


}
