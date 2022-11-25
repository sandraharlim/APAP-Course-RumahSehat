package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.PasienModel;

public interface PasienRestService {
    PasienModel getPasienById(String uuid);

    PasienModel topUpSaldo(String uuid, PasienModel pasien);

    void updateSaldo(String uuid, Long saldo);
}
