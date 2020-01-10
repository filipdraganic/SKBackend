package com.example.demo.service;

import com.example.demo.dao.KorisnikDao;
import com.example.demo.dao.MailDto;
import com.example.demo.model.Korisnik;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

            if(korisnik.getMikroservisi().get("Vremenska prognoza") == 1) {
                //System.out.println(korisnici.toString());

                int temperature = weatherApirequest("http://localhost:9000/weather/?country=Serbia&city=Belgrade");

                Date date = new Date();
                String response = "Trenutna temperatura u Beogradu je " + temperature +" stepeni celzijusa. \n \n Zelimo vam prijatan dan";

                MailDto mailDto = new MailDto("skserveremail@gmail.com", "Trenutna temperatura u " + date.getTime(), response);
                outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
                logger.info("Hello");
            }

            if(korisnik.getMikroservisi().get("XKCD meme") == 2) {
//                System.out.println(korisnici.toString());

                String response = XKCDApirequest("http://localhost:9000/xkcd");

                MailDto mailDto = new MailDto("skserveremail@gmail.com", "XKCD mema dana" , response);
                outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
                logger.info("Hello");
            }

        }

    }
    @Scheduled(fixedDelay = 30*60000)
    public void publishNaTridesetMinuta(){
        List<Korisnik> korisnici = korisnikDao.findAll();

        for(Korisnik korisnik : korisnici){

            if(korisnik.getMikroservisi().get("Vremenska prognoza") == 2) {
//                System.out.println(korisnici.toString());

                weatherApirequest("http://localhost:9000/weather/?country=Serbia&city=Belgrade");

                MailDto mailDto = new MailDto(korisnik.getEmail(), "Zdravo", "Zdravo zdravo");
                outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
                logger.info("Hello");
            }

            if(korisnik.getMikroservisi().get("XKCD meme") == 2) {
//                System.out.println(korisnici.toString());

                XKCDApirequest("localhost:9000/weather/?country=England&city=London");

                MailDto mailDto = new MailDto(korisnik.getEmail(), "Zdravo", "Zdravo zdravo");
                outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
                logger.info("Hello");
            }

        }
    }

    @Scheduled(fixedDelay = 60*60000)
    public void publishNaSatVremena(){
        List<Korisnik> korisnici = korisnikDao.findAll();

        for(Korisnik korisnik : korisnici){

            if(korisnik.getMikroservisi().get("Vremenska prognoza") == 3) {
                //System.out.println(korisnici.toString());

                weatherApirequest("http://localhost:9000/weather/?country=Serbia&city=Belgrade");

                MailDto mailDto = new MailDto(korisnik.getEmail(), "Zdravo", "Zdravo zdravo");
                outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
                logger.info("Hello");
            }

            if(korisnik.getMikroservisi().get("XKCD meme") == 3) {
                System.out.println(korisnici.toString());

                XKCDApirequest("localhost:9000/weather/?country=England&city=London");

                MailDto mailDto = new MailDto(korisnik.getEmail(), "Zdravo", "Zdravo zdravo");
                outputChannel.output().send(MessageBuilder.withPayload(mailDto).build());
                logger.info("Hello");
            }
        }
    }


    public int weatherApirequest(String GET_URL)  {

        try{

            URL url = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            System.out.println(content);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(String.valueOf(content));

            System.out.println(node.get("temperature").asDouble());

            return node.get("temperature").asInt();

        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -10000000;
    }

    public String XKCDApirequest(String GET_URL)  {

        try{

            URL url = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            System.out.println(content);


            return content.toString();

        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Link trenutno ne postoji";
    }



}
