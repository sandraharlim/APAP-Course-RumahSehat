package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.restmodel.DokterDropdownItem;
import TA_A_ME_61.RumahSehat.restmodel.ResponseNewAppointment;
import TA_A_ME_61.RumahSehat.restmodel.SubmittedAppointment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Positive;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// @RequestMapping("/")
public class UserRestController {

    @Autowired
    private DokterService dokterService;

    @Autowired
    private PasienService pasienService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // dokter dan apoteker ditambahkan oleh admin, 
    // jd gaperlu ditambahin disini.

    // registrasi pasien melalui mobile, jd perlu dimasukin kesini

    // gada regis admin, adanya login dr sso.

//    @PostMapping("/sign-up/pasien")
//    public void signUpPasien(@RequestBody PasienModel pasien) {
//        pasien.setSaldo(0L);
//        pasien.setRole("Pasien");
//        List<AppointmentModel> ls = new ArrayList<>();
//        pasien.setListAppointment(ls);
//        pasien.setPassword(passwordEncoder.encode(pasien.getPassword()));
//        pasienService.addPasien(pasien);
//    }

}
