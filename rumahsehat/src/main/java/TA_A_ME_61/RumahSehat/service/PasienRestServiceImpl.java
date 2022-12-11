package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AdminModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public void addPasien(PasienModel pasien) {
        String encryptedPass = encrypt(pasien.getPassword());
        pasien.setPassword(encryptedPass);
        pasienDb.save(pasien);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public PasienModel getPasienById(String uuid) {
        Optional<PasienModel> pasien = pasienDb.findByUuid(uuid);
        if (pasien.isPresent()) {
            return pasien.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public PasienModel topUpSaldo(String uuid, PasienModel pasienBaru) {
        PasienModel pasienLama = getPasienById(uuid);
        Long saldoTerbaru = pasienLama.getSaldo() + pasienBaru.getSaldo();
        pasienLama.setSaldo(saldoTerbaru);
        return pasienDb.save(pasienLama);
    }

    @Override
    public void updateSaldo(String username, Long saldo) {
        PasienModel pasienLama = getPasienByUsername(username);
        Long saldoTerbaru = pasienLama.getSaldo() + saldo;
        pasienLama.setSaldo(saldoTerbaru);
        pasienDb.save(pasienLama);
    }

    @Override
    public PasienModel getPasienByUsername(String username){
        PasienModel pasien = pasienDb.findByUsername(username);
        return pasien;
    }

}
