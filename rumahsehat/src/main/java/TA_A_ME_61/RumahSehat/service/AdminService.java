package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AdminModel;

import java.util.List;

public interface AdminService {
    void addAdmin(AdminModel admin);

    AdminModel getAdminByUsername(String username);

}
