package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;

import java.util.List;

public interface PasienService {
    PasienModel getPasienByUsername(String username);

    List<PasienModel> getListPasien();

    PasienModel getPasienByUuid(String uuid);

    void deletePasien(PasienModel pasien);

    void addPasien(PasienModel pasien);

}
