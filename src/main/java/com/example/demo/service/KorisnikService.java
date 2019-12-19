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
        try{

            korisnikDao.save(new Korisnik(ObjectId.get(), name, email, password));

            System.out.println("Uspeo");
        }catch(Exception err){
            System.out.println("ERROR");
        }
    }

    public List<Korisnik> getKorisnici(){
        return korisnikDao.findAll();
    }

    public int getKorisnik(String email){


            System.out.println("AAAAA");
            if(korisnikDao.findByEmail(email) == null){
                System.out.println("Nema duplikata USPEH!");
                return 1;
            }
        System.out.println("Ima duplikata");
        System.out.println(-1);

        return -1;
    }

    public int getKorisnik(String email, String password){
        List<Korisnik> korisnici = null;
        try {

            System.out.println("AAAAA");
            Korisnik toReturn = korisnikDao.findByEmail(email);

            if(toReturn.getPassword().equals(password)){
                System.out.println("LOGIN");
                return 1;
            }
            else return 0;


        }catch (Exception error){

            System.out.println("ERROR ispod");

            return -1;
        }


    }

    public void deleteSve(){
        korisnikDao.deleteAll();
    }


}
