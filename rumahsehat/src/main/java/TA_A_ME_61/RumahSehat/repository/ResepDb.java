package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.ResepModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResepDb extends JpaRepository<ResepModel, Long> {
}
