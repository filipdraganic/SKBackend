package com.example.demo.api;

import com.example.demo.model.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.KorisnikService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;


////////////////// REQUEST -> KONTROLER -> SERVIS -> DATABASE

@RequestMapping("api/v1/korisnik")
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    @Autowired
    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;

    }

    @PostMapping
    public void addKorisnik(@RequestBody Korisnik korisnik){
        korisnikService.addKorisnik(korisnik);
    }

    @GetMapping
    public ArrayList<Korisnik> getKorisnici(){
        return korisnikService.getKorisnici();
    }

    @GetMapping(path = "{id}")
    public Korisnik getKorisnikById(@PathVariable("id") UUID id){
        return korisnikService.getKorsnikById(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public int deleteKorisnikById(@PathVariable("id") UUID id){
        return korisnikService.deleteKorisnik(id);
    }

    @PutMapping(path = "{id}")
    public int updateKorisnik(@PathVariable UUID id,@Valid @RequestBody Korisnik noviKorisnik){

        return korisnikService.updateKorisnik(id, noviKorisnik);
    }


}
