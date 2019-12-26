package com.example.demo.service;

import com.example.demo.dao.KorisnikDao;
import com.example.demo.dao.MailDto;
import com.example.demo.model.Korisnik;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.springframework.http.HttpHeaders.USER_AGENT;


@Component
@EnableBinding(OutputChannel.class)
@RequiredArgsConstructor

public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger("SchedulerService");

    private final OutputChannel outputChannel;

    private final KorisnikDao korisnikDao;

    @Autowired
    public Scheduler(KorisnikDao korisnikDao, OutputChannel outputChannel) {
        this.korisnikDao = korisnikDao;
        this.outputChannel = outputChannel;

    }

    @Scheduled(fixedDelay = 10000)
    public void publish(){
        List<Korisnik> korisnici = korisnikDao.findAll();

        for(Korisnik korisnik : korisnici){

            System.out.println(korisnici.toString());

            apirequest("localhost:9000/weather/?country=England&city=London");

            MailDto mailDto = new MailDto(korisnik.getEmail(),  "Zdravo", "Zdravo zdravo");
            outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
            logger.info("Hello");

        }
    }

    public void apirequest(String GET_URL){

        try {
            URL obj = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        }catch (IOException ioex){
            System.out.println(ioex.getMessage());
        }


    }


}
