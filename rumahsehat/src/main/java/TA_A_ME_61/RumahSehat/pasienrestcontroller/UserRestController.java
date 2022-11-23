package TA_A_ME_61.RumahSehat.pasienrestcontroller;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.service.PasienRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pasien")
public class UserRestController {

    @Autowired
    private PasienRestService pasienRestService;

    @GetMapping("/profile")
    private PasienModel getPasien(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        try {
            return pasienRestService.getPasienById(token);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, " User Id " + token + " Tidak ada"
            );
        }
    }
    @GetMapping("/profile/{uuid}")
    private PasienModel cekPostman(@PathVariable("uuid") String uuid) {
//        System.out.println(token);
        try {
            return pasienRestService.getPasienById(uuid);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, " User Id " + uuid + " Tidak ada"
            );
        }
    }


}
