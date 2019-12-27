package com.example.demo.service;

import com.example.demo.model.Korisnik;
import com.example.demo.dao.KorisnikDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public int getKorisnik(Object email, Object password){

        try{
            Korisnik korisnik = korisnikDao.findByEmailAndPassword(email.toString(),password.toString());
            System.out.println(korisnik.toString());
            System.out.println(korisnik.getMikroservisi());
            return 1;

        }catch (NullPointerException exception){
            System.out.println("NULLLLL");

        }
        return -1;
    }

    public int patchKorisnik(Object email, Object imeServisa){

        try{
            Korisnik korisnik = korisnikDao.findByEmail(email.toString());
            korisnik.getMikroservisi().put(imeServisa.toString(),-1*korisnik.getMikroservisi().get(imeServisa.toString()));
            korisnikDao.save(korisnik);
            return 1;

        }catch (NullPointerException exception){
            System.out.println("Null u Patch korisnik");
        }


        return -1;
    }


    public int patchKorisnikSubscriptions(Object email, Object imeServisa, Object podesavanje){

        try{
            Korisnik korisnik = korisnikDao.findByEmail(email.toString());

            System.out.println("dovde?");
            System.out.println("korisnik je =  " + korisnik.toString());
            System.out.println("Ime mikroservisa je = "+ imeServisa.toString());
            System.out.println("Vrednost mikroservisa je =  "+ korisnik.getMikroservisi().get(imeServisa.toString()));

            int mikroservisPodesavanje = korisnik.getMikroservisi().get(imeServisa.toString());
            if(mikroservisPodesavanje < 0){
                mikroservisPodesavanje /= mikroservisPodesavanje;
                mikroservisPodesavanje *= -1;
                mikroservisPodesavanje *= Integer.parseInt(podesavanje.toString());
            }
            else if(mikroservisPodesavanje > 0){
                mikroservisPodesavanje /= mikroservisPodesavanje;
                mikroservisPodesavanje *= Integer.parseInt(podesavanje.toString());
            }
            else{
                mikroservisPodesavanje = -1;
            }
            System.out.println("mikroservis "+ mikroservisPodesavanje);
            korisnik.getMikroservisi().put(imeServisa.toString(),mikroservisPodesavanje);
            korisnikDao.save(korisnik);
            return 1;

        }catch (NullPointerException exception){
            System.out.println("Null u Patch korisnikSubscriptions");
        }


        return -1;

    }

    public Map<String, Integer> getSubscriptions(String email){

        Map<String, Integer> failsafeMapa = new HashMap<>();
        failsafeMapa.put("Vremenska prognoza", -1);
        failsafeMapa.put("XKCD meme", -1);
        failsafeMapa.put("Pracenje akcija", -1);
        System.out.println("email = " +email);
        try{
            Korisnik korisnik = korisnikDao.findByEmail(email);
            System.out.println(korisnik.getMikroservisi());
            return korisnik.getMikroservisi();
        }catch (NullPointerException exception){
            System.out.println("Null u get Subscriptions");
        }
        return failsafeMapa;
    }




    public void deleteSve(){
        korisnikDao.deleteAll();
    }


}
