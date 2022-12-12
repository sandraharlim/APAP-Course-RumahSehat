package TA_A_ME_61.RumahSehat.restcontroller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import TA_A_ME_61.RumahSehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserRestController {

    @Autowired
    private DokterService dokterService;

    @Autowired
    private PasienService pasienService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

}
