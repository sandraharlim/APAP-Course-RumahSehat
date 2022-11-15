package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.JumlahModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JumlahDb extends JpaRepository<JumlahModel, Long> {
    Optional<JumlahModel> deleteJumlahModelByObat_IdObat(String idObat);
}
