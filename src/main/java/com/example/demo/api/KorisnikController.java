package com.example.demo.api;

import com.example.demo.model.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.KorisnikService;

import java.util.List;
import java.util.Map;


////////////////// REQUEST -> KONTROLER -> SERVIS -> DATABASE

@RequestMapping("api/korisnik")
@RestController
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
    @PostMapping
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


    @CrossOrigin(origins = "http://localhost:8090")
    @GetMapping
    public List<Korisnik> getKorisnici(){
        System.out.println("Nesto");
        return korisnikService.getKorisnici();
    }


    @CrossOrigin(origins = "http://localhost:8090")
    @PostMapping("/login")
    public int loginUser(@RequestBody Map<String, Object> korisnik){
        int vratiti = -1;
        if(korisnik.get("email").toString().equals("") || korisnik.get("password").toString().equals("")){

            return vratiti;
        }
        vratiti = korisnikService.getKorisnik(korisnik.get("email"),korisnik.get("password"));

        return vratiti;
    }

    @CrossOrigin(origins = "http://localhost:8090")
    @PostMapping("/logintest")
    public int loginTest(@RequestBody Map<String, Object> korisnik){

        System.out.println(korisnik.get("email").toString() + "  " + korisnik.get("password").toString());
        return korisnikService.getKorisnik(korisnik.get("email"), korisnik.get("password"));


    }
    @CrossOrigin(origins = "http://localhost:8090")
    @PatchMapping("/subscribe")
    public int patchKorisnik(@RequestBody Map<String, Object> korisnik){

        System.out.println(korisnik.get("email").toString() + "  " + korisnik.get("imeServisa").toString());
        return korisnikService.patchKorisnik(korisnik.get("email"), korisnik.get("imeServisa"));
    }

    @CrossOrigin(origins = "http://localhost:8090")
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

    @CrossOrigin(origins = "http://localhost:8090")
    @GetMapping("/getSubscriptions")
    @ResponseBody
    public Map<String, Integer> getSubscriptions(@RequestParam(name="id") String email){

        System.out.println(email);
        return korisnikService.getSubscriptions(email);
    }


}
