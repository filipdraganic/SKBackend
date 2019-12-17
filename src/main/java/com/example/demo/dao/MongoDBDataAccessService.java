package com.example.demo.dao;

import com.example.demo.model.Korisnik;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@Repository("mongodb")
public class MongoDBDataAccessService implements KorisnikDao {

    private final MongoDatabase database;
    private final MongoClient mongoClient;


    @Autowired
    public MongoDBDataAccessService() {
        MongoClientURI connectionString = new MongoClientURI("spring.data.mongodb.uri= \"mongodb+srv://admin:admin@clusterkorisnika-rtz4a.mongodb.net/test?retryWrites=true&w=majority\"");
        mongoClient = new MongoClient(connectionString);

        database = mongoClient.getDatabase("mydb");

    }

    @Override
    public int insertKorisnik(UUID id, Korisnik korisnik) {
        return 0;
    }

    @Override
    public int insertKorisnik(Korisnik korisnik) {
        return 0;
    }

    @Override
    public ArrayList<Korisnik> getKorisnici() {
        ArrayList<Korisnik> lista = new ArrayList<>();
        MongoCollection<Document> kolekcijaKorisnika = database.getCollection("korisnici");

        kolekcijaKorisnika.insertOne(new Korisnik(UUID.randomUUID(), "MONGODB KORISNIK"));

        lista.add(new Korisnik(UUID.randomUUID(), "MONGODB KORISNIK"));

        MongoCursor<Document> cursor = kolekcijaKorisnika.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toString());
            }
        } finally {
            cursor.close();
        }



        return lista;
    }

    @Override
    public Optional<Korisnik> selectKorisnikById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deleteKorisnikById(UUID id) {
        return 0;
    }

    @Override
    public int updateKorisnikById(UUID id, Korisnik korisnik) {
        return 0;
    }

    @Override
    public Korisnik getKorisnik(String name) {
        return null;
    }

    @Override
    public Korisnik getKorisnik(UUID id) {
        return null;
    }
}
