package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.JumlahModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JumlahDb extends JpaRepository<JumlahModel, Long> {

}