package com.example.demo.api;

import com.example.demo.model.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.KorisnikService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


////////////////// REQUEST -> KONTROLER -> SERVIS -> DATABASE

@RequestMapping("api/korisnik")
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    @Autowired
    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;

    }


    @DeleteMapping
    public void deleteSve(){
        korisnikService.deleteSve();
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping
    public int addKorisnik(@RequestBody Korisnik korisnik){
        if(korisnik.getName().equals("") || korisnik.getEmail().equals("") || korisnik.getPassword().equals("")){
            return 0;
        }
        try{

            korisnikService.getKorisnik(korisnik.getEmail(),korisnik.getPassword());

        }catch(Exception error){
            System.out.println("ERROR");
            return -1;
        }

        korisnikService.addKorisnik(korisnik.getName(), korisnik.getEmail(), korisnik.getPassword());
        return 1;
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping
    public List<Korisnik> getKorisnici(){
        System.out.println("Nesto");
        return korisnikService.getKorisnici();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/login")
    public int loginUser(@RequestBody Korisnik korisnik){
        int vratiti = -1;
        if(korisnik.getEmail().equals("") || korisnik.getPassword().equals("")){

            return vratiti;
        }
        vratiti = korisnikService.getKorisnik(korisnik.getEmail(),korisnik.getPassword());


        return vratiti;


    }
    @GetMapping("/logintest")
    public int loginTest(@RequestBody Korisnik korisnik){

        return korisnikService.getKorisnik(korisnik.getEmail());

    }





}
