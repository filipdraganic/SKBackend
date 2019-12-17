package com.example.demo.service;

import com.example.demo.model.Korisnik;
import com.example.demo.dao.KorisnikDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@Service
public class KorisnikService {

    private final KorisnikDao korisnikDao;

    @Autowired
    public KorisnikService(@Qualifier("mongodb")KorisnikDao korisnikDao) {
        this.korisnikDao = korisnikDao;
    }




    public int addKorisnik( Korisnik korisnik){
        return korisnikDao.insertKorisnik(korisnik);
    }

    public Korisnik getKorisnik(String name){
        return korisnikDao.getKorisnik(name);
    }

    public Korisnik getKorisnik(UUID id){
        return korisnikDao.getKorisnik(id);
    }

    public ArrayList<Korisnik> getKorisnici(){
        return korisnikDao.getKorisnici();
    }


    public Optional<Korisnik> getKorsnikById(UUID id){
        return korisnikDao.selectKorisnikById(id);
    }

    public int deleteKorisnik(UUID id){
        return korisnikDao.deleteKorisnikById(id);
    }

    public int updateKorisnik(UUID id, Korisnik noviKorisnik){

        return korisnikDao.updateKorisnikById(id, noviKorisnik);
    }

}
