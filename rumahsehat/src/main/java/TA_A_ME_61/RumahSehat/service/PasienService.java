package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;

public interface PasienService {
    PasienModel getPasienByUuid(String uuid);
    PasienModel getPasienByUsername(String username);
}
