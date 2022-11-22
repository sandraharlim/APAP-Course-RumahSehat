package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.PasienModel;

import java.util.List;

public interface PasienService {
    List<PasienModel> getListPasien();

    PasienModel getPasienByUuid(String uuid);

    void deletePasien(PasienModel pasien);


}
