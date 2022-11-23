package TA_A_ME_61.RumahSehat.pasienrestcontroller;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.service.PasienRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pasien")
public class PasienRestController {

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

//    @PutMapping("/profile/update-saldo")
//    private PasienModel topUpSaldo(@RequestHeader("Authorization") String token, @RequestBody PasienModel pasien) {
//        System.out.println("Token top up saldo uuid: " + token);
//        System.out.println(pasien);
//        try {
//            return pasienRestService.topUpSaldo(token, pasien);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "User Id " + token + " Tidak ada"
//            );
//        }
//    }

    @PutMapping("/profile/update-saldo")
    private String topUpSaldo(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> saldo) {
        System.out.println("Token top up saldo uuid: " + token);
        System.out.println(saldo);
        try {
            pasienRestService.updateSaldo(token, Long.valueOf(saldo.get("saldo")));
            return "Success";

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Id " + token + " Tidak ada"
            );
        }
    }
}
