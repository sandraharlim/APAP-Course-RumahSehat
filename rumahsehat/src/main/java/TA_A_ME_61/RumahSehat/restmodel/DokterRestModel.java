package TA_A_ME_61.RumahSehat.restmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DokterRestModel {
    private String uuid;
    private String nama;
    private String username;
    private String email;
    private String password;
    private Long tarif;

}
