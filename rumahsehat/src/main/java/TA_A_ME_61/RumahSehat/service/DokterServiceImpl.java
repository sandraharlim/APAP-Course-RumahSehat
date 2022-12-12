package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AdminModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DokterServiceImpl implements DokterService{
    @Autowired
    DokterDb dokterDb;

    @Override
    public void addDokter(DokterModel dokter) {
        String encryptedPass = encrypt(dokter.getPassword());
        dokter.setPassword(encryptedPass);
        dokterDb.save(dokter);
    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<DokterModel> getListDokter() {
        return dokterDb.findAll();
    }

    @Override
    public DokterModel getDokterByUuid(String uuid) {
        Optional<DokterModel> dokter = dokterDb.findByUuid(uuid);
        if (dokter.isPresent()) {
            return dokter.get();
        } else {
            return null;
        }
    }

    @Override
    public void updateDokter(DokterModel dokter){
        dokterDb.save(dokter);
    }

    @Override
    public void deleteDokter(DokterModel dokter) {
        dokterDb.delete(dokter);
    }

    @Override
    public DokterModel getDokterByUsername(String username){
        return dokterDb.findByUsername(username);
    }

    @Override
    public List<DokterModel> findAllDokter() {
        return dokterDb.findAll();
    }

}
