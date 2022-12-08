package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PasienServiceImpl implements PasienService{
    @Autowired
    PasienDb pasienDb;

    @Override
    public PasienModel getPasienByUsername(String username) {
        return pasienDb.findByUsername(username);
    }
    
    @Override
    public List<PasienModel> getListPasien() {
        return pasienDb.findAll();
    }

    @Override
    public PasienModel getPasienByUuid(String uuid) {
        Optional<PasienModel> pasien = pasienDb.findByUuid(uuid);
        if (pasien.isPresent()) {
            return pasien.get();
        } else {
            return null;
        }
    }

    @Override
    public void deletePasien(PasienModel pasien) {
        pasienDb.delete(pasien);
    }

    @Override
    public void addPasien(PasienModel pasien){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode(pasien.getPassword());
        pasien.setPassword(pass);
        pasienDb.save(pasien);
    }
}
