package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class PasienRestServiceImpl implements PasienRestService{

    @Autowired
    private PasienDb pasienDb;

    @Override
    public PasienModel getPasienById(String uuid) {
        Optional<PasienModel> pasien = pasienDb.findByUuid(uuid);
        if (pasien.isPresent()) {
            return pasien.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
