package com.example.demo.dao;

import com.example.demo.model.Korisnik;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository("fakeDao")
public class FakeKorisnikDataAccessService implements KorisnikDao{

    private static ArrayList<Korisnik> DB = new ArrayList<>();


    @Override
    public int insertKorisnik(UUID id, Korisnik korisnik) {
        DB.add(new Korisnik(id,korisnik.getName()));
        return 1;
    }

    @Override
    public ArrayList<Korisnik> getKorisnici() {
        return (ArrayList<Korisnik>) DB;
    }

    @Override
    public Korisnik getKorisnik(String name){
        ArrayList<Korisnik> korisnici = DB;
        Korisnik nullKorisnik = null;

        for(Object o : korisnici){
            if (o instanceof Korisnik){
                if(((Korisnik) o).getName().contains(name)){
                    return (Korisnik)o;
                }
            }
        }
        return nullKorisnik;

    }
    @Override
    public Korisnik getKorisnik(UUID id){
        ArrayList<Korisnik> korisnici = DB;
        Korisnik nullKorisnik = null;

        for(Object o : korisnici){
            if (o instanceof Korisnik){
                if(((Korisnik) o).getId().equals(id)){
                    return (Korisnik)o;
                }
            }
        }
        return nullKorisnik;

    }

    @Override
    public Optional<Korisnik> selectKorisnikById(UUID id) {
        return DB.stream()
                .filter(korisnik -> korisnik.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteKorisnikById(UUID id) {

        for(Object o : DB){
            if(o instanceof Korisnik){
                if(((Korisnik) o).getId().equals(id)){
                    DB.remove(o);
                    return 1;
                }
            }

        }

        return 0;
    }

    @Override
    public int updateKorisnikById(UUID id, Korisnik korisnik) {

        for(Object o : DB){
            if(o instanceof Korisnik){
                System.out.println(((Korisnik) o).getId() + " = " + id );
                if(((Korisnik) o).getId().equals(id)){
                    int indexToReplace = DB.indexOf(o);
                    DB.set(indexToReplace, new Korisnik(id, korisnik.getName()));


                    return 1;
                }
            }

        }
        return 0;
    }


}
