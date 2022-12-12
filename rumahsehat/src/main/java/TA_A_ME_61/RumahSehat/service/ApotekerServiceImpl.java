package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.ApotekerModel;
import TA_A_ME_61.RumahSehat.repository.ApotekerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApotekerServiceImpl implements ApotekerService{
    @Autowired
    ApotekerDb apotekerDb;

    @Override
    public void addApoteker(ApotekerModel apoteker) {
        String encryptedPass = encrypt(apoteker.getPassword());
        apoteker.setPassword(encryptedPass);
        apotekerDb.save(apoteker);
    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
    @Override
    public List<ApotekerModel> getListApoteker() {
        return apotekerDb.findAll();
    }

    @Override
    public ApotekerModel getApotekerByUuid(String uuid) {
        Optional<ApotekerModel> apoteker = apotekerDb.findByUuid(uuid);
        if (apoteker.isPresent()) {
            return apoteker.get();
        } else {
            return null;
        }
    }

    @Override
    public void deleteApoteker(ApotekerModel apoteker) {
        apotekerDb.delete(apoteker);
    }

    @Override
    public ApotekerModel getApotekerByUsername(String username){
        return apotekerDb.findByUsername(username);
    }


}
