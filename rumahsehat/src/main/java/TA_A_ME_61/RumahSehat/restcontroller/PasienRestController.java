package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.service.PasienRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pasien")
public class PasienRestController {

    @Autowired
    private PasienRestService pasienRestService;

    @GetMapping("/profile")
    private PasienModel getPasien() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            System.out.println(username);
            PasienModel ps = pasienRestService.getPasienByUsername(username);
            return ps;
        } catch (NoSuchElementException e) {
            System.out.println("CATCH");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Failed"
            );
        }
    }

    @PutMapping("/profile/update-saldo")
    private void topUpSaldo(@RequestBody Map<String, Long> saldo) {
        System.out.println(saldo);
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

    @PostMapping("/register")
    private void registerPasien(@Valid @RequestBody PasienModel pasien, BindingResult bindingResult) {
        System.out.println("ADDED");
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        } else {
            pasienRestService.addPasien(pasien);
        }
    }
}
