package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PasienServiceImpl implements PasienService{
    @Autowired
    PasienDb pasienDb;

    @Override
    public PasienModel getPasienByUuid(String uuid) {
        Optional<PasienModel> pasien = pasienDb.findById(uuid);
        if (pasien.isPresent()) {
            return pasien.get();
        } else return null;
    }
}
