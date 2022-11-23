package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt(String password);

    UserModel getUserByUsername(String username);
    List<UserModel> findAll();

    void delete(UserModel userModel);
}
