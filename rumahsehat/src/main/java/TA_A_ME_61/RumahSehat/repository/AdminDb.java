package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.AdminModel;
import TA_A_ME_61.RumahSehat.model.ApotekerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDb extends JpaRepository<AdminModel,String>  {
    AdminModel findByUsername(String username);
}
