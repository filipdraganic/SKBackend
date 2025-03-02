package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;


@Document(collection = "korisnici")
public class Korisnik {

    @Id
    private ObjectId id = null;
    @NotBlank
    private String name = null;

    private HashMap<String, Integer>  mikroservisi = new HashMap<>();
    @Email
    private final String email;

    private final String password;

    public Korisnik(@JsonProperty("id") ObjectId id,
                    @JsonProperty("name") String name,
                    @JsonProperty("email") String email,
                    @JsonProperty("password") String password
                    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        mikroservisi.put("Vremenska prognoza", -1);
        mikroservisi.put("Pracenje akcija", -1);
        mikroservisi.put("XKCD meme", -1);
    }



    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getMikroservisi(){
        return this.mikroservisi;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }



    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mikroservisi=" + mikroservisi +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int compareTo(Korisnik korisnik){
        if(this.getEmail().equals(korisnik.getEmail()) && this.getPassword().equals(this.getEmail())){
            return 1;
        }
        else return 0;
    }
}
