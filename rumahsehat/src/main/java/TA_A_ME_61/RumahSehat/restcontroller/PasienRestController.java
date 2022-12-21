package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.restmodel.PasienDTO;
import TA_A_ME_61.RumahSehat.service.PasienRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/pasien")
public class PasienRestController {

    @Autowired
    private PasienRestService pasienRestService;

    @GetMapping("/profile")
    private PasienDTO getPasien() {
        log.info("User mencoba melihat halaman profilenya");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        PasienModel pasien = pasienRestService.getPasienByUsername(username);

        PasienDTO pasienDTO = pasienRestService.getPasienDto(pasien);
        return pasienDTO;
    }

    @PutMapping("/profile/update-saldo")
    private void topUpSaldo(@RequestBody Map<String, Long> saldo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            log.info("User berhasil menambah saldo miliknya di RumahSehat");
            pasienRestService.updateSaldo(username, saldo.get("saldo"));

        } catch (NoSuchElementException e) {
            log.info("User tidak berhasil menambah saldo miliknya di RumahSehat");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Id Tidak ada"
            );
        }
    }
}
