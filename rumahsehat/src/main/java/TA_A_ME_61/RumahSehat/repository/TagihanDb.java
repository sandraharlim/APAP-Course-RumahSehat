package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.TagihanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagihanDb extends JpaRepository<TagihanModel,Long> {
    //JPA
    Optional<TagihanModel> findById(Long id);
}
