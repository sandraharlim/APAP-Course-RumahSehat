//package TA_A_ME_61.RumahSehat.service;
//
//import TA_A_ME_61.RumahSehat.model.UserModel;
//import TA_A_ME_61.RumahSehat.repository.UserDb;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserServiceImpl implements UserService{
//    @Autowired
//    private UserDb userDb;
//
//    @Override
//    public UserModel addUser(UserModel user) {
//        String encryptedPass = encrypt(user.getPassword());
//        user.setPassword(encryptedPass);
//        return userDb.save(user);
//    }
//
//    @Override
//    public String encrypt(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
//        return hashedPassword;
//    }
//
//    @Override
//    public UserModel getUserByUsername(String username) {
//        UserModel user = userDb.findByUsername(username);
//        return user;
//    }
//
//    @Override
//    public List<UserModel> findAll() {
//        return userDb.findAll();
//    }
//
//    @Override
//    public void delete(UserModel userModel) {
//        userDb.delete(userModel);
//    }
//}