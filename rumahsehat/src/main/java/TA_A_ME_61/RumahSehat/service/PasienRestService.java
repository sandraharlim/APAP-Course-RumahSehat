package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.restmodel.PasienDTO;

public interface PasienRestService {

    void addPasien(PasienModel pasien);
    PasienModel getPasienById(String uuid);

    PasienModel topUpSaldo(String uuid, PasienModel pasien);

    void updateSaldo(String uuid, Long saldo);

    PasienModel getPasienByUsername(String username);

    String encrypt(String password);

    PasienDTO getPasienDto(PasienModel pasien);
}
