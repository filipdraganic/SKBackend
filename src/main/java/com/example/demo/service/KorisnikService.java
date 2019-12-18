package com.example.demo.service;

import com.example.demo.model.Korisnik;
import com.example.demo.dao.KorisnikDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KorisnikService {

    private final KorisnikDao korisnikDao;

    @Autowired
    public KorisnikService(KorisnikDao korisnikDao) {
        this.korisnikDao = korisnikDao;
    }
//
//    public Korisnik getKorisnik(String name){
//        return korisnikDao
//    }

    public void addKorisnik(String name,String email, String password){
        korisnikDao.save(new Korisnik(ObjectId.get(), name, email, password));
    }

    public List<Korisnik> getKorisnici(){
        return korisnikDao.findAll();
    }

    public int getKorisnik(String email){
        List<Korisnik> korisnici = null;
        try {

            System.out.println("AAAAA");
            if(korisnikDao.findByEmail(email) == null){
                System.out.println("USPEH!");
                return 1;
            }
            System.out.println("korisnici = " + korisnici.size());

        }catch (Exception error){

            System.out.println("ERROR ispod");

            return -1;
        }
        return 11111;
    }

    public int getKorisnik(String email, String password){



        return 0;
    }

    public void deleteSve(){
        korisnikDao.deleteAll();
    }


}
