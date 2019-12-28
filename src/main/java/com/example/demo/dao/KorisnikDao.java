package com.example.demo.dao;

import com.example.demo.model.Korisnik;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface KorisnikDao extends MongoRepository<Korisnik,String> {

    Korisnik findByEmail(String email);

    Korisnik findByEmailAndPassword(String email, String password);




}
