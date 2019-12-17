package com.example.demo.dao;

import com.example.demo.model.Korisnik;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface KorisnikDao {

    int insertKorisnik(UUID id, Korisnik korisnik);

    default int insertKorisnik(Korisnik korisnik){
        UUID id = UUID.randomUUID();
        return insertKorisnik(id,korisnik);
    }

    ArrayList<Korisnik> getKorisnici();

    Optional<Korisnik> selectKorisnikById(UUID id);

    int deleteKorisnikById(UUID id);

    int updateKorisnikById(UUID id, Korisnik korisnik);

    Korisnik getKorisnik(String name);

    Korisnik getKorisnik(UUID id);



}
