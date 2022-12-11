package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.service.PasienRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pasien")
public class PasienRestController {

    @Autowired
    private PasienRestService pasienRestService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    private PasienModel getPasien() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        PasienModel pasien = pasienRestService.getPasienByUsername(username);
        return pasien;
    }

    @PutMapping("/profile/update-saldo")
    private void topUpSaldo(@RequestBody Map<String, Long> saldo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            pasienRestService.updateSaldo(username, saldo.get("saldo"));

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Id Tidak ada"
            );
        }
    }
}
