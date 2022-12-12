package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.AdminModel;
import TA_A_ME_61.RumahSehat.model.ApotekerModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.restmodel.JwtRequest;
import TA_A_ME_61.RumahSehat.restmodel.JwtResponse;
import TA_A_ME_61.RumahSehat.restmodel.PasienDTO;
import TA_A_ME_61.RumahSehat.security.JwtTokenUtil;
import TA_A_ME_61.RumahSehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private ApotekerService apotekerService;

    @Autowired
    private PasienService pasienService;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        log.info("User mencoba membuat token jwt baru");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            log.info("User berhasil mendapatkan token jwt baru");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody PasienDTO pasienDTO){
        DokterModel dokter = dokterService.getDokterByUsername(pasienDTO.getUsername());
        ApotekerModel apoteker = apotekerService.getApotekerByUsername(pasienDTO.getUsername());
        PasienModel pasienModel = pasienService.getPasienByUsername(pasienDTO.getUsername());
        AdminModel admin = adminService.getAdminByUsername(pasienDTO.getUsername());

        if (dokter == null && apoteker == null && pasienModel == null && admin == null){
            PasienModel pasien = new PasienModel();
            pasien.setNama(pasienDTO.getNama());
            pasien.setUsername(pasienDTO.getUsername());
            pasien.setEmail(pasienDTO.getEmail());
            pasien.setPassword(pasienDTO.getPassword());
            pasien.setUmur(pasienDTO.getUmur());
            pasien.setRole("Pasien");
            pasien.setSaldo(0L);
            pasienService.addPasien(pasien);

            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(pasien.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);

            log.info("User berhasil membuat akun baru RumahSehat");
            return ResponseEntity.ok(new JwtResponse(token));
        }

        log.info("User tidak berhasil membuat akun baru RumahSehat karena username sudah tersedia");
        return new ResponseEntity<>("Username sudah ada.", HttpStatus.BAD_REQUEST);
    }
}

