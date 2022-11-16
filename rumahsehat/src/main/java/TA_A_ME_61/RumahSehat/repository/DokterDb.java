package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.DokterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DokterDb extends JpaRepository<DokterModel,String> {
}
