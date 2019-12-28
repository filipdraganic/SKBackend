package com.example.demo.api;

import com.example.demo.jwttoken.JwtRequest;
import com.example.demo.jwttoken.JwtResponse;
import com.example.demo.jwttoken.JwtTokenUtil;
import com.example.demo.jwttoken.JwtUserDetailsService;
import com.example.demo.model.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.KorisnikService;

import java.util.List;
import java.util.Map;


////////////////// REQUEST -> KONTROLER -> SERVIS -> DATABASE

@RequestMapping("api/korisnik")
@RestController
@CrossOrigin
public class KorisnikController {


    private final KorisnikService korisnikService;


    @Autowired
    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;

    }


    @DeleteMapping
    public void deleteSve(){
        korisnikService.deleteSve();
    }


    @CrossOrigin(origins = "http://localhost:8090")
    @PostMapping("/register")
    public int addKorisnik(@RequestBody Korisnik korisnik){
        if(korisnik.getName().equals("") || korisnik.getEmail().equals("") || korisnik.getPassword().equals("")){
            return 0;
        }
        try{
           int res =  korisnikService.getKorisnik(korisnik.getEmail());
           if(res == -1){
               return -1;
           }

        }catch(Exception error){
            System.out.println("ERROR");
            return -1;
        }


        korisnikService.addKorisnik(korisnik.getName(), korisnik.getEmail(), korisnik.getPassword());
        return 1;
    }


    @GetMapping
    public List<Korisnik> getKorisnici(){
        System.out.println("Nesto");
        return korisnikService.getKorisnici();
    }


    @PostMapping("/login")
    public int loginUser(@RequestBody Map<String, Object> korisnik){
        int vratiti = -1;
        System.out.println("LoginUser stigao ovde");
        System.out.println("korisnik = "+ korisnik);
        if(korisnik.get("email").toString().equals("") || korisnik.get("password").toString().equals("")){

            return vratiti;
        }
        vratiti = korisnikService.getKorisnik(korisnik.get("email"),korisnik.get("password"));

        return vratiti;
    }

    @PostMapping("/logintest")
    public int loginTest(@RequestBody Map<String, Object> korisnik){

        System.out.println(korisnik.get("email").toString() + "  " + korisnik.get("password").toString());
        return korisnikService.getKorisnik(korisnik.get("email"), korisnik.get("password"));


    }
    @PatchMapping("/subscribe")
    public int patchKorisnik(@RequestBody Map<String, Object> korisnik){

        System.out.println(korisnik.get("email").toString() + "  " + korisnik.get("imeServisa").toString());
        return korisnikService.patchKorisnik(korisnik.get("email"), korisnik.get("imeServisa"));
    }

    @PatchMapping("/subscribeSettings")
    public int patchKorisnikSubscriptions(@RequestBody Map<String, Object> korisnik){

        System.out.println(korisnik.get("email").toString() + "  " + korisnik.get("imeServisa").toString() + " " + korisnik.get("podesavanje").toString());
        return korisnikService.patchKorisnikSubscriptions(korisnik.get("email"), korisnik.get("imeServisa"), korisnik.get("podesavanje"));
    }

//    @CrossOrigin(origins = "http://localhost:8090")
//    @GetMapping("/getSubscriptions")
//    public Map<String,Integer> getSubscriptions(@RequestBody Map<String, Object> korisnik){
//
//        System.out.println(korisnik.get("email").toString()  + " trazi subskripcije da vidi");
//        return korisnikService.getSubscriptions(korisnik.get("email").toString());
//    }

    @GetMapping("/getSubscriptions")
    @ResponseBody
    public Map<String, Integer> getSubscriptions(@RequestParam(name="id") String email){

        System.out.println(email);
        return korisnikService.getSubscriptions(email);
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @CrossOrigin
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        System.out.println("requestMapping authenticate");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    private void authenticate(String username, String password) throws Exception {
        System.out.println("authenticate");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
