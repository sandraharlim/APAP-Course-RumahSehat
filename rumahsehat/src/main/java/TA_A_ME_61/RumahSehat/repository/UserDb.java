package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDb extends JpaRepository<UserModel, String> {
}
