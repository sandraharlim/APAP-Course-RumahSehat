package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.ApotekerModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApotekerDb extends JpaRepository<ApotekerModel,String>  {
    //JPA
    Optional<ApotekerModel> findByUuid(String uuid);
}
