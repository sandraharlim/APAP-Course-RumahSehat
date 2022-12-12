package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AdminModel;
import TA_A_ME_61.RumahSehat.repository.AdminDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDb adminDb;

    @Override
    public void addAdmin(AdminModel admin) {
        String encryptedPass = encrypt(admin.getPassword());
        admin.setPassword(encryptedPass);
        adminDb.save(admin);

    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public AdminModel getAdminByUsername(String username){
        return adminDb.findByUsername(username);
    }
}
