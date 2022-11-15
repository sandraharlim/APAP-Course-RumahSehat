package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.ObatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObatDb extends JpaRepository<ObatModel, String> {

//    Optional<ObatModel> findByIdObat(String idObat);
}
