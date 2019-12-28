package com.example.demo.jwttoken;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dao.KorisnikDao;
import com.example.demo.model.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final KorisnikDao korisnikDao;

    @Autowired
    public JwtUserDetailsService(KorisnikDao korisnikDao) {
        this.korisnikDao = korisnikDao;
    }

    private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Korisnik> korisnici = korisnikDao.findAll();

        for(Korisnik korisnik: korisnici){
            String email= korisnik.getEmail();
            if (email.equals(username)) {
                return new User(email, hash(korisnik.getPassword()),
                        new ArrayList<>());
            }
        }
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
}
